package io.art9.alaya.chat.gateway.mqtt

import io.art9.alaya.chat.gateway.AuthFailed
import io.art9.alaya.chat.gateway.AuthService
import io.art9.alaya.chat.gateway.AuthSuccess
import io.netty.handler.codec.mqtt.MqttConnectReturnCode
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.mqtt.MqttServer
import mu.KLogging

data class MqttOptions(val port: Int)

open class MqttVerticle(
    private val options: MqttOptions,
    private val mqttHandlersFactory: MqttHandlersFactory,
    private val authService: AuthService
) :
    AbstractVerticle() {

    override fun start(startPromise: Promise<Void>?) {
        val mqttServer = MqttServer.create(vertx)
        mqttServer
            .endpointHandler { endpoint ->
                if (endpoint.auth() == null) {
                    endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_AUTHENTICATION_METHOD)
                }

                authService.auth(endpoint.auth().username, endpoint.auth().password)
                    .onSuccess {
                        when (it) {
                            is AuthSuccess -> {

                                val session = MqttSession.create(endpoint)
                                endpoint
                                    .publishHandler(mqttHandlersFactory.publishHandler(session))
                                    .publishReleaseHandler(mqttHandlersFactory.publishReleaseHandler(session))
                                    .closeHandler(mqttHandlersFactory.closeHandler(session))
                                    .disconnectHandler(mqttHandlersFactory.disconnectHandler(session))
                                    .subscribeHandler(mqttHandlersFactory.subscribeHandler(session))
                                    .exceptionHandler(mqttHandlersFactory.exceptionHandler(session))
                                    .pingHandler(mqttHandlersFactory.pingHandler(session))
                                    .accept()
                            }
                            is AuthFailed -> {
                                endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD)
                            }
                            else -> {}
                        }
                    }
                    .onFailure {
                        endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD)
                    }


            }
            .listen(options.port)
            .onComplete {
                if (it.succeeded()) {
                    logger.info { "MQTT server started, listening on port ${options.port}" }
                    startPromise?.complete()
                } else {
                    logger.error(it.cause()) { "MQTT server start failed" }
                    startPromise?.fail(it.cause())
                }
            }
    }

    companion object : KLogging()

}