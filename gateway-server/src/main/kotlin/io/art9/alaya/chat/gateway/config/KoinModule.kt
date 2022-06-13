package io.art9.alaya.chat.gateway.config

import io.art9.alaya.chat.gateway.AuthService
import io.art9.alaya.chat.gateway.handler.DefaultMqttHandlersFactory
import io.art9.alaya.chat.gateway.mqtt.MqttHandlersFactory
import io.art9.alaya.chat.gateway.mqtt.MqttGatewayServer
import io.art9.alaya.chat.gateway.AuthServiceImpl
import io.art9.alaya.chat.gateway.mqtt.MqttSecurityPolicy
import io.art9.alaya.chat.message.SecurityPolicy
import io.art9.alaya.chat.message.SessionStore
import io.vertx.mqtt.MqttEndpoint
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


object KoinModule {
    val main = module {
        singleOf(::DefaultMqttHandlersFactory) { bind<MqttHandlersFactory>() }
        factory { Configuration.mqttOptions(get()) }
        singleOf(::AuthServiceImpl) { bind<AuthService>() }
        factory { SessionStore() }
        singleOf(::MqttSecurityPolicy) { bind<SecurityPolicy<MqttSecurityPolicy.ClientInfo, MqttEndpoint>>() }
        factory { MqttGatewayServer(get(), get(), get(), get(), get()) }
    }
}