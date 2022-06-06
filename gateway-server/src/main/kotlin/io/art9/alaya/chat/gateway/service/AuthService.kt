package io.art9.alaya.chat.gateway

import io.vertx.core.Future

interface AuthService {

    fun auth(username: String, password: String): Future<AuthResult>
}

sealed interface AuthResult

data class AuthSuccess(val username: String, val password: String) : AuthResult

data class AuthFailed(val reason: String) : AuthResult

open class AuthServiceImpl : AuthService {
    override fun auth(username: String, password: String): Future<AuthResult> {
        if (username == "alaya" && password == "123456") {
            return Future.succeededFuture(AuthSuccess(username, password))
        }
        return Future.succeededFuture(AuthFailed("TODO"))
    }
}