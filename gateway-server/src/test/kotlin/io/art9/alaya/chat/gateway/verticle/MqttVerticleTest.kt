package io.art9.alaya.chat.gateway.verticle

import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.mqtt.MqttClient
import mu.KLogging
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
internal class MqttVerticleTest {

    private val vertx = Vertx.vertx()

    @Test
    open fun test_mqttConnect() {
        val testContext = VertxTestContext()

        val mqtt = MqttClient.create(vertx)

        mqtt.connect(8080, "127.0.0.1") {
            if (it.succeeded()) {
                mqtt.publish("hello", Buffer.buffer("hello world"), MqttQoS.AT_LEAST_ONCE, false, false)
                    .onSuccess { msgId ->
                        logger.info { "publish message $msgId" }
                        testContext.succeedingThenComplete<Void>()
                        mqtt.disconnect()
                    }
            } else {
                logger.error(it.cause()) { "failed" }
                testContext.failingThenComplete<Void>()
            }

        }
        assert(testContext.awaitCompletion(5, TimeUnit.SECONDS))
        if (testContext.failed()) {
            throw testContext.causeOfFailure();
        }
    }

    companion object : KLogging()
}