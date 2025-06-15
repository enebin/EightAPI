package com.enebin

import io.ktor.server.application.*
import plugins.DatabaseFactory

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init(environment.config)

    configureCors()
    configureRouting()
    configureMonitoring()
    configureSerialization()
}
