package io.art9.alaya.chat.message

import io.vertx.core.Future

interface Transport : Cloneable {

    fun sendMessage(message: Message): Future<Void>
}