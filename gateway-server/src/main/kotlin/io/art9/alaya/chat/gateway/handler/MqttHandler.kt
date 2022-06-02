package io.art9.alaya.chat.gateway.handler

import io.art9.alaya.chat.gateway.verticle.MqttHandlers
import io.vertx.core.Handler
import io.vertx.mqtt.messages.MqttSubscribeMessage
import mu.KLogging

open class DefaultMqttHandlers : MqttHandlers {


    companion object : KLogging()

    override fun closeHandler(): Handler<Void> = Handler {
        logger.info { "on close" }
    }

    override fun disconnectHandler(): Handler<Void> = Handler {
        logger.info { "on disconnect" }
    }

    override fun subscribeHandler(): Handler<MqttSubscribeMessage> {
        TODO("Not yet implemented")
    }

    override fun exceptionHandler(): Handler<Throwable> {
        TODO("Not yet implemented")
    }

    override fun pingHandler(): Handler<Void> {
        TODO("Not yet implemented")
    }
}