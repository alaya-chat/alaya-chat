package io.art9.alaya.chat.message

import io.vertx.core.Future

interface SecurityPolicy<T : SecurityPolicy.ClientInfo, C> {

    fun authenticateConnection(connection: C): Future<Result<T>>

    fun releaseConnection(client: T)

    interface ClientInfo
}