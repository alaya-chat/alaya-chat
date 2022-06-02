package io.art9.alaya.chat.gateway.handler

import io.art9.alaya.chat.gateway.verticle.MqttHandlersFactory
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.mqtt.MqttEndpoint
import io.vertx.mqtt.messages.MqttPublishMessage
import io.vertx.mqtt.messages.MqttSubscribeMessage
import mu.KLogging
import java.util.concurrent.atomic.AtomicInteger

open class DefaultMqttHandlersFactory() : MqttHandlersFactory {
    override fun publishReleaseHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Int> = Handler { messageId ->
        logger.info { "Publish release message $messageId" }
    }

    override fun publishHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<MqttPublishMessage> =
        Handler { publish ->
            logger.info { "Received PUBLISH message: ${publish.payload().toString("UTF8")}" }
        }


    override fun closeHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void> = Handler {
        logger.info { "on close" }
    }

    override fun disconnectHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void> = Handler {
        logger.info { "on disconnect" }
    }

    override fun subscribeHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<MqttSubscribeMessage> =
        Handler { subscribe ->
            logger.info { "Receive SUBSCRIBE message $subscribe" }
        }

    override fun exceptionHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Throwable> = Handler { err ->
        logger.error(err) { "handle exception" }
    }

    override fun pingHandler(vertx: Vertx, endpoint: MqttEndpoint): Handler<Void> = Handler {
        logger.info { "Receive PING message" }
    }

    companion object : KLogging() {
        var counter = AtomicInteger(0)
    }
}