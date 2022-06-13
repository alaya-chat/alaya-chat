package io.art9.alaya.chat.message.codec

import io.art9.alaya.chat.message.Message
import io.vertx.core.buffer.Buffer

interface MessageEncoder<O> {
    fun <T> encode(message: Message<T>): Result<O>
}

class MockMessageEncoder : MessageEncoder<Buffer> {
    override fun <T> encode(message: Message<T>): Result<Buffer> {
        return Result.success(Buffer.buffer("MOCK IT"))
    }
}