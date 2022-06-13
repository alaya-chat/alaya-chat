package io.art9.alaya.chat.gateway

import io.art9.alaya.chat.gateway.config.KoinModule
import io.art9.alaya.chat.gateway.mqtt.MqttGatewayServer
import io.art9.kylinx.core.util.runApplication
import io.art9.kylinx.koin.verticle.KoinVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Future
import io.vertx.core.Promise
import org.koin.core.component.get
import org.koin.core.module.Module

open class GatewayApp : KoinVerticle() {

    override fun afterStarted(): Future<Void> {
        return vertx.deployVerticle({ get<MqttGatewayServer>() }, DeploymentOptions().setInstances(8))
            .mapEmpty<Void>()
    }

    override fun koinModules(): List<Module> {
        return listOf(KoinModule.main)
    }
}

fun main(args: Array<String>) {
    runApplication(GatewayApp::class.java, args)
}
