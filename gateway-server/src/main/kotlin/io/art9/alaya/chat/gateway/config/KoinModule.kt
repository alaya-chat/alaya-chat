package io.art9.alaya.chat.gateway.config

import io.art9.alaya.chat.gateway.handler.DefaultMqttHandlersFactory
import io.art9.alaya.chat.gateway.verticle.MqttHandlersFactory
import io.art9.alaya.chat.gateway.verticle.MqttVerticle
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


object KoinModule {
    val main = module {
        singleOf(::DefaultMqttHandlersFactory) { bind<MqttHandlersFactory>() }
        factory { Configuration.mqttOptions(get()) }
        factory { MqttVerticle(get(), get()) }
    }
}