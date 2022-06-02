package io.art9.alaya.chat.gateway.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.mqtt.MqttServer
import io.vertx.mqtt.messages.MqttSubscribeMessage
import mu.KLogging

data class MqttOptions(val port: Int)

interface MqttHandlers {
    fun closeHandler(): Handler<Void>

    fun disconnectHandler(): Handler<Void>

    fun subscribeHandler(): Handler<MqttSubscribeMessage>

    fun exceptionHandler(): Handler<Throwable>

    fun pingHandler(): Handler<Void>
}

open class MqttVerticle(private val options: MqttOptions, private val mqttHandlers: MqttHandlers) : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        val mqttServer = MqttServer.create(vertx)
        mqttServer
            .endpointHandler { endpoint ->

                endpoint.closeHandler(mqttHandlers.closeHandler())
                endpoint.disconnectHandler(mqttHandlers.disconnectHandler())
                endpoint.subscribeHandler(mqttHandlers.subscribeHandler())
                endpoint.exceptionHandler(mqttHandlers.exceptionHandler())
                endpoint.pingHandler(mqttHandlers.pingHandler())

                endpoint.accept()
            }
            .listen(options.port)
            .onComplete {
                if (it.succeeded()) {
                    logger.info { "MQTT server started, listening on port ${options.port}" }
                    startPromise?.complete()
                } else {
                    logger.error(it.cause()) { "MQTT server start failed" }
                    startPromise?.fail(it.cause())
                }
            }
    }

    companion object : KLogging()

}