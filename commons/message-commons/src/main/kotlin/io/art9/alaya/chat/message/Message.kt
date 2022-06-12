package io.art9.alaya.chat.message

interface Message<T> {

    fun topics(): List<String>

    fun payload(): T

    fun headers(): Map<String, Any>
}

open class DefaultMessage<T>(
    private val topics: List<String>,
    private val payload: T,
    private val headers: Map<String, Any> = mapOf()
) : Message<T> {
    override fun topics(): List<String> {
        return topics
    }

    override fun payload(): T {
        return payload
    }

    override fun headers(): Map<String, Any> {
        return headers
    }
}