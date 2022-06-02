package io.art9.alaya.chat.gateway.config

import io.art9.alaya.chat.gateway.handler.DefaultMqttHandlers
import io.art9.alaya.chat.gateway.verticle.MqttHandlers
import io.art9.alaya.chat.gateway.verticle.MqttVerticle
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


object KoinModule {
    val main = module {
        singleOf(::DefaultMqttHandlers) { bind<MqttHandlers>() }
        factory { Configuration.mqttOptions(get()) }
        factory { MqttVerticle(get(), get()) }
    }
}