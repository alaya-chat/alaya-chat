package io.art9.alaya.chat.gateway.mqtt

import io.art9.alaya.chat.gateway.AuthService
import io.art9.alaya.chat.message.SecurityPolicy
import io.art9.alaya.chat.message.Session
import io.art9.alaya.chat.message.SessionManager
import io.art9.alaya.chat.message.codec.MockMessageEncoder
import io.netty.handler.codec.mqtt.MqttConnectReturnCode
import io.netty.handler.codec.mqtt.MqttProperties
import io.netty.handler.codec.mqtt.MqttProperties.StringProperty
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.mqtt.MqttEndpoint
import io.vertx.mqtt.MqttServer
import mu.KLogging

data class MqttOptions(val port: Int)

open class MqttGatewayServer(
    private val options: MqttOptions,
    private val mqttHandlersFactory: MqttHandlersFactory,
    private val authService: AuthService,
    private val securityPolicy: SecurityPolicy<MqttSecurityPolicy.ClientInfo, MqttEndpoint>,
    private val sessionManager: SessionManager
) :
    AbstractVerticle() {

    private fun handleConnection(endpoint: MqttEndpoint) {

        securityPolicy.authenticateConnection(endpoint)
            .onSuccess {
                if (it.isSuccess) {
                    val transport = MqttTransport(endpoint, MockMessageEncoder())
                    val session = Session.establish(transport, it.getOrNull()!!)
                    sessionManager.add(session)
                    endpoint.closeHandler {
                        transport.beforeSessionRemoved()
                        sessionManager.remove(session.id())
                        transport.afterSessionRemoved()
                    }

                    endpoint.disconnectHandler {
                        transport.beforeSessionRemoved()
                        sessionManager.remove(session.id())
                        transport.afterSessionRemoved()
                    }
                    val props = MqttProperties()
                    props.add(StringProperty(0, endpoint.clientIdentifier()))
                    endpoint.accept(true, props)
                } else {
                    val result = it.exceptionOrNull()
                    logger.error(result) { "Authenticate connection failed" }
                    endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD)
                }
            }
            .onFailure {
                logger.error(it) { "Authenticate connection failed" }
                endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE)
            }
    }

    override fun start(startPromise: Promise<Void>?) {
        val mqttServer = MqttServer.create(vertx)
        mqttServer
            .endpointHandler { endpoint ->
                handleConnection(endpoint)
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