package io.art9.alaya.chat.message

import io.vertx.core.Future
import mu.KLogging

/**
 * Session
 */
class Session(
    val transport: Transport,
    val clientInfo: SecurityPolicy.ClientInfo
) {

    fun sendMessage(message: Message): Future<Void> {
        TODO()
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