package io.art9.alaya.chat.message

interface SecurityPolicy<T : SecurityPolicy.ClientInfo, C> {

    fun authenticateConnection(connection: C): T

    fun releaseConnection(client: T)

    interface ClientInfo {

    }
}