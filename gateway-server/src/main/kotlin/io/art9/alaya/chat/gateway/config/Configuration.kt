package io.art9.alaya.chat.gateway.config

import io.art9.alaya.chat.gateway.mqtt.MqttOptions
import io.vertx.core.Vertx

object Configuration {

    fun mqttOptions(vertx: Vertx): MqttOptions {
        val port = vertx.orCreateContext.config()
            .getJsonObject("mqtt")
            ?.getJsonObject("server")
            ?.getInteger("port", 8080)
            ?: 8080
        return MqttOptions(port)
    }
}