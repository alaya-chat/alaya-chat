package io.art9.alaya.chat.message

import io.vertx.core.Future

/**
 * Session
 */
interface Session {

    fun id(): String

    fun <T> sendMessage(message: Message<T>): Future<Void>
}