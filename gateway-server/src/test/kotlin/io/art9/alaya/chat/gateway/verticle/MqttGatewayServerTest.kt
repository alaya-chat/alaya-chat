package io.art9.alaya.chat.gateway.verticle

import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.mqtt.MqttClient
import mu.KLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
internal class MqttGatewayServerTest {

    private val vertx = Vertx.vertx()

    @Test
    open fun test_mqttConnect() {
        val testContext = VertxTestContext()

        val client = MqttClient.create(vertx)
        val uid = 1001

        client.connect(8080, "127.0.0.1")
            .compose { ack ->
                val sessionId = ack.properties().getProperty(0)
                client
                    .subscribe(
                        mapOf(
                            "/session/$sessionId" to MqttQoS.AT_LEAST_ONCE.value(),
                            "/user/$uid" to MqttQoS.AT_LEAST_ONCE.value()
                        )
                    )
            }
            .compose {
                client.publish(
                    "room.message.publish",
                    Buffer.buffer("hello world"),
                    MqttQoS.AT_LEAST_ONCE,
                    false,
                    false
                )
            }

        assert(testContext.awaitCompletion(5, TimeUnit.SECONDS))
        if (testContext.failed()) {
            throw testContext.causeOfFailure();
        }
    }

    companion object : KLogging()
}