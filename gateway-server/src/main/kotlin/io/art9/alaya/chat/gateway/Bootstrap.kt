package io.art9.alaya.chat.gateway

import io.art9.alaya.chat.gateway.config.Configuration
import io.art9.alaya.chat.gateway.handler.DefaultMqttHandler
import io.art9.alaya.chat.gateway.verticle.MqttHandler
import io.art9.alaya.chat.gateway.verticle.MqttVerticle
import io.art9.kylinx.core.util.runApplication
import io.art9.kylinx.koin.verticle.KoinVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Promise
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

open class Bootstrap : KoinVerticle() {

    override fun start(promise: Promise<Void>) {
        vertx.deployVerticle({ get<MqttVerticle>() }, DeploymentOptions().setInstances(8))
            .mapEmpty<Void>()
            .onComplete(promise)
    }


    override fun koinModules(): List<Module> {
        return listOf(module {
            singleOf(::DefaultMqttHandler) { bind<MqttHandler>() }
            factory { Configuration.mqttOptions(vertx) }
            factory { MqttVerticle(get(), get()) }
        })
    }
}

fun main(args: Array<String>) {
    runApplication(Bootstrap::class.java, args)
}
