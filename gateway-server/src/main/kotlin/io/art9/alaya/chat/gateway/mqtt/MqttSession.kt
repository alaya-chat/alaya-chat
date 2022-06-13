package io.art9.alaya.chat.gateway.mqtt

import io.art9.alaya.chat.message.Message
import io.art9.alaya.chat.message.Session
import io.vertx.core.Future
import io.vertx.mqtt.messages.MqttPublishMessage
import io.vertx.mqtt.messages.MqttSubscribeMessage
import io.vertx.mqtt.messages.MqttUnsubscribeMessage
import mu.KLogging

class MqttSession(
    private val clientInfo: MqttSecurityPolicy.ClientInfo,
    private val transport: MqttTransport,
) : Session {

    companion object : KLogging() {
        fun establish(
            transport: MqttTransport,
            clientInfo: MqttSecurityPolicy.ClientInfo,
        ): MqttSession {
            return MqttSession(clientInfo, transport)
        }
    }

    override fun id(): String {
        return clientInfo.id()
    }

    fun onClose() {
        transport.onClose(this)
    }

    fun onDisconnect() {
        transport.onClose(this)
    }

    fun onSubscribe(msg: MqttSubscribeMessage) {
        transport.onSubscribe(this, msg)
    }

    fun onPublish(msg: MqttPublishMessage) {
        transport.onPublish(this, msg)
    }

    fun onPing() {
        transport.onPing(this)
    }

    fun onUnsubscribe(msg: MqttUnsubscribeMessage) {
        transport.onUnsubscribe(this, msg)
    }


    override fun <T> sendMessage(message: Message<T>): Future<Void> {
        return transport.sendMessage(message)
    }
}