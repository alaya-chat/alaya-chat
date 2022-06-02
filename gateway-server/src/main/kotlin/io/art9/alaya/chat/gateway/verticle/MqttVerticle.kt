package io.art9.alaya.chat.gateway.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.mqtt.MqttEndpoint
import io.vertx.mqtt.MqttServer
import io.vertx.mqtt.messages.MqttSubscribeMessage
import mu.KLogging

data class MqttOptions(val port: Int)

interface MqttHandlersFactory {
    fun closeHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void>

    fun disconnectHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void>

    fun subscribeHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<MqttSubscribeMessage>

    fun exceptionHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Throwable>

    fun pingHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void>
}

open class MqttVerticle(private val options: MqttOptions, private val mqttHandlersFactory: MqttHandlersFactory) :
    AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        val mqttServer = MqttServer.create(vertx)
        mqttServer
            .endpointHandler { endpoint ->
                endpoint.closeHandler(mqttHandlersFactory.closeHandler(vertx, endpoint))
                endpoint.disconnectHandler(mqttHandlersFactory.disconnectHandler(vertx, endpoint))
                endpoint.subscribeHandler(mqttHandlersFactory.subscribeHandler(vertx, endpoint))
                endpoint.exceptionHandler(mqttHandlersFactory.exceptionHandler(vertx, endpoint))
                endpoint.pingHandler(mqttHandlersFactory.pingHandler(vertx, endpoint))
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