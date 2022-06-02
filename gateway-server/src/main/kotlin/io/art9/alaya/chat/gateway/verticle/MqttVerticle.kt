package io.art9.alaya.chat.gateway.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.mqtt.MqttServer
import mu.KLogging

data class MqttOptions(val port: Int)

interface MqttHandler {
    fun onClose()

    fun onDisconnect()
}

open class MqttVerticle(private val options: MqttOptions, private val mqttHandler: MqttHandler) : AbstractVerticle() {


    override fun start(startPromise: Promise<Void>?) {
        val mqttServer = MqttServer.create(vertx)
        mqttServer
            .endpointHandler { endpoint ->
                endpoint.closeHandler { mqttHandler.onClose() }
                endpoint.disconnectHandler { mqttHandler.onDisconnect() }

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