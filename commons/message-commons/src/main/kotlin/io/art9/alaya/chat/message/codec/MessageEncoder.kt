package io.art9.alaya.chat.message.codec

import io.art9.alaya.chat.message.Message
import io.vertx.core.buffer.Buffer

interface MessageEncoder {
    fun <T> encode(message: Message<T>): Result<Buffer>
}