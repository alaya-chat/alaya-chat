package io.art9.alaya.chat.message

import java.util.concurrent.ConcurrentHashMap

class SessionManager {

    private val store = ConcurrentHashMap<String, Session>()

    fun add(session: Session) {
        store[session.id()] = session
    }

    fun remove(id: String): Session? {
        return store.remove(id)
    }
}