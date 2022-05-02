package io.art9.alaya.chat.gateway

import io.art9.alaya.chat.gateway.server.MqttServer
import io.quarkus.runtime.StartupEvent
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.enterprise.inject.Instance

@ApplicationScoped
open class Bootstrap {

    fun init(@Observes ev: StartupEvent, vertx: Vertx, verticle: Instance<MqttServer>) {
        vertx.deployVerticle(verticle::get, DeploymentOptions().setInstances(8))
    }
}
