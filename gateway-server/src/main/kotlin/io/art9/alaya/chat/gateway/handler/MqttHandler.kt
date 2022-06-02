package io.art9.alaya.chat.gateway.handler

import io.art9.alaya.chat.gateway.verticle.MqttHandlersFactory
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.mqtt.MqttEndpoint
import io.vertx.mqtt.messages.MqttSubscribeMessage
import mu.KLogging
import java.util.concurrent.atomic.AtomicInteger

open class DefaultMqttHandlersFactory() : MqttHandlersFactory {


    override fun closeHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void> = Handler {
        logger.info { "on close" }
    }

    override fun disconnectHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void> = Handler {
        logger.info { "on disconnect" }
    }

    override fun subscribeHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<MqttSubscribeMessage> {
        TODO("Not yet implemented")
    }

    override fun exceptionHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Throwable> {
        TODO("Not yet implemented")
    }

    override fun pingHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void> {
        TODO("Not yet implemented")
    }

    companion object : KLogging() {
        var counter = AtomicInteger(0)
    }
}