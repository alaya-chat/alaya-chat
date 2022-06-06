package io.art9.alaya.chat.gateway.mqtt

import io.vertx.core.Handler
import io.vertx.mqtt.messages.MqttPublishMessage
import io.vertx.mqtt.messages.MqttSubscribeMessage

interface MqttHandlersFactory {

    fun publishReleaseHandler(session: MqttSession): Handler<Int>

    fun publishHandler(session: MqttSession): Handler<MqttPublishMessage>

    fun closeHandler(session: MqttSession): Handler<Void>

    fun disconnectHandler(session: MqttSession): Handler<Void>

    fun subscribeHandler(session: MqttSession): Handler<MqttSubscribeMessage>

    fun exceptionHandler(session: MqttSession): Handler<Throwable>

    fun pingHandler(session: MqttSession): Handler<Void>
}