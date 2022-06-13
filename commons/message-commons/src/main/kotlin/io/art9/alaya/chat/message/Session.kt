package io.art9.alaya.chat.message

import io.vertx.core.Future
import mu.KLogging

/**
 * Session
 */
class Session(
    private val transport: Transport,
    val clientInfo: SecurityPolicy.ClientInfo
) {

    fun <T> sendMessage(message: Message<T>): Future<Void> {
        return transport.sendMessage(message)
    }

    companion object : KLogging() {
        /**
         * Establish session
         */
        fun establish(transport: Transport, clientInfo: SecurityPolicy.ClientInfo): Session {
            return Session(transport, clientInfo)
        }
    }
}