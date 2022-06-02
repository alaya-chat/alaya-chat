package io.art9.alaya.chat.gateway.handler

import io.art9.alaya.chat.gateway.verticle.MqttHandler
import mu.KLogging

open class DefaultMqttHandler : MqttHandler {
    override fun onClose() {
        logger.info { "onClose" }
    }

    override fun onDisconnect() {
        logger.info { "onDisconnect" }
    }

    companion object : KLogging()
}