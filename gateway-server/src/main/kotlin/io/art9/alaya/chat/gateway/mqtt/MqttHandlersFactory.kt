package io.art9.alaya.chat.gateway.mqtt

import io.art9.alaya.chat.message.Session
import io.vertx.core.Handler
import io.vertx.mqtt.messages.MqttPublishMessage
import io.vertx.mqtt.messages.MqttSubscribeMessage

interface MqttHandlersFactory {

    fun publishReleaseHandler(session: Session): Handler<Int>

    fun publishHandler(session: Session): Handler<MqttPublishMessage>

    fun closeHandler(session: Session): Handler<Void>

    fun disconnectHandler(session: Session): Handler<Void>

    fun subscribeHandler(session: Session): Handler<MqttSubscribeMessage>

    fun exceptionHandler(session: Session): Handler<Throwable>

    fun pingHandler(session: Session): Handler<Void>
}