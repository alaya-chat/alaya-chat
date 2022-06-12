package io.art9.alaya.chat.gateway

import io.art9.alaya.chat.gateway.config.KoinModule
import io.art9.alaya.chat.gateway.mqtt.MqttGatewayServer
import io.art9.kylinx.core.util.runApplication
import io.art9.kylinx.koin.verticle.KoinVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Promise
import org.koin.core.component.get
import org.koin.core.module.Module

open class GatewayApp : KoinVerticle() {

    override fun start(promise: Promise<Void>) {
        vertx.deployVerticle({ get<MqttGatewayServer>() }, DeploymentOptions().setInstances(8))
            .mapEmpty<Void>()
            .onComplete(promise)
    }


    override fun koinModules(): List<Module> {
        return listOf(KoinModule.main)
    }
}

fun main(args: Array<String>) {
    runApplication(GatewayApp::class.java, args)
}
