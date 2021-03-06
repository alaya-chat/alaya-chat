package io.art9.alaya.chat.gateway.mqtt

import io.art9.alaya.chat.message.Message
import io.art9.alaya.chat.message.Session
import io.art9.alaya.chat.message.Transport
import io.art9.alaya.chat.message.codec.MessageEncoder
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.buffer.Buffer
import io.vertx.mqtt.MqttEndpoint
import io.vertx.mqtt.messages.MqttPublishMessage
import io.vertx.mqtt.messages.MqttSubscribeMessage
import io.vertx.mqtt.messages.MqttUnsubscribeMessage

class MqttTransport(
    private val endpoint: MqttEndpoint,
    private val messageEncoder: MessageEncoder<Buffer>,
) : Transport {

    override fun afterSessionRemoved(session: Session) {
    }

    override fun beforeSessionRemoved(session: Session) {
    }

    fun onPing(session: Session) {
    }

    fun onSubscribe(session: Session, mqttSubscribeMessage: MqttSubscribeMessage) {

    }

    fun onUnsubscribe(session: Session, mqttUnsubscribeMessage: MqttUnsubscribeMessage) {

    }

    fun onPublish(session: Session, message: MqttPublishMessage) {
    }


    fun onClose(session: Session) {
    }

    fun onOpen(session: Session) {
    }

    override fun <T> sendMessage(message: Message<T>): Future<Void> = CompositeFuture
        .all(
            message.topics()
                .map { topic ->
                    Future
                        .future<Buffer> { h ->
                            messageEncoder.encode(message)
                                .onSuccess { h.complete() }
                                .onFailure { h.fail(it.cause) }
                        }
                        .map { buf ->
                            endpoint.publish(topic, buf, MqttQoS.AT_LEAST_ONCE, false, false)
                        }
                }
        )
        .mapEmpty()
}