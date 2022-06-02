package io.art9.alaya.chat.gateway.server

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.mqtt.MqttServer
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.Dependent

@Dependent
open class Server(
    @ConfigProperty(name = "mqtt.server.port")
    private val port: Int
) : AbstractVerticle() {


    override fun start(startPromise: Promise<Void>?) {
        val mqttServer = MqttServer.create(vertx)
        mqttServer
            .endpointHandler { endpoint ->
                endpoint.closeHandler {

                }

            }
            .listen(port)
            .onComplete {
                if (it.succeeded()) {
                    startPromise?.complete()
                } else {
                    startPromise?.fail(it.cause())
                }
            }
    }
}