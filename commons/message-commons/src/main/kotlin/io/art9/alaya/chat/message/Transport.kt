package io.art9.alaya.chat.message

import io.vertx.core.Future

interface Transport : Cloneable {

    fun <T> sendMessage(message: Message<T>): Future<Void>
    fun afterSessionRemoved(session: Session)
    fun beforeSessionRemoved(session: Session)
}