package io.art9.alaya.chat.gateway.mqtt

import io.art9.alaya.chat.message.SecurityPolicy
import io.vertx.core.Future
import io.vertx.mqtt.MqttEndpoint

class MqttSecurityPolicy : SecurityPolicy<MqttSecurityPolicy.ClientInfo, MqttEndpoint> {

    class ClientInfo(val id: String) : SecurityPolicy.ClientInfo {
        override fun id(): String {
            return id
        }
    }

    override fun authenticateConnection(connection: MqttEndpoint): Future<Result<ClientInfo>> {
        return Future.succeededFuture(Result.success(ClientInfo(connection.clientIdentifier())))
    }

    override fun releaseConnection(client: ClientInfo) {
        TODO("Not yet implemented")
    }
}