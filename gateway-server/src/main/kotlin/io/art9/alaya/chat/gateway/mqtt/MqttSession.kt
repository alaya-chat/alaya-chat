package io.art9.alaya.chat.gateway.mqtt

import io.vertx.mqtt.MqttEndpoint
import mu.KLogging
import java.util.UUID

class MqttSession(
    val id: String,
    val username: String,
    val endpoint: MqttEndpoint
) {

    companion object : KLogging() {

        fun create(endpoint: MqttEndpoint): MqttSession {
            val id = UUID.randomUUID().toString()
            return MqttSession(id, endpoint.auth().username, endpoint)
        }
    }
}