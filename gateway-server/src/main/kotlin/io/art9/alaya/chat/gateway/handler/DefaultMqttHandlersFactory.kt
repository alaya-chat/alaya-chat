package io.art9.alaya.chat.gateway.handler

import io.art9.alaya.chat.gateway.mqtt.MqttHandlersFactory
import io.art9.alaya.chat.message.Session
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.mqtt.messages.MqttPublishMessage
import io.vertx.mqtt.messages.MqttSubscribeMessage
import mu.KLogging
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.atomic.AtomicInteger

open class DefaultMqttHandlersFactory : MqttHandlersFactory {

    private val vertx: Vertx by inject(Vertx::class.java)
    override fun publishReleaseHandler(session: Session): Handler<Int> = Handler { messageId ->
        logger.info { "Publish release message $messageId" }
        session.publishRelease(messageId)
    }

    override fun publishHandler(session: Session): Handler<MqttPublishMessage> =
        Handler { publish ->
            val topic = publish.topicName()
            val prop = publish.properties()
            val ctype = prop.getProperty(0)
            if (ctype.value() == "json") {

            } else {

            }
            logger.info { "Received PUBLISH message: ${publish.payload().toString("UTF8")}" }
        }


    override fun closeHandler(session: Session): Handler<Void> = Handler {
        logger.info { "on close" }
    }

    override fun disconnectHandler(session: Session): Handler<Void> = Handler {
        logger.info { "on disconnect" }
    }

    override fun subscribeHandler(session: Session): Handler<MqttSubscribeMessage> =
        Handler { subscribe ->
            logger.info { "Receive SUBSCRIBE message $subscribe" }
        }

    override fun exceptionHandler(session: Session): Handler<Throwable> = Handler { err ->
        logger.error(err) { "handle exception" }
    }

    override fun pingHandler(session: Session): Handler<Void> = Handler {
        logger.info { "Receive PING message" }
    }

    companion object : KLogging() {
        var counter = AtomicInteger(0)
    }
}