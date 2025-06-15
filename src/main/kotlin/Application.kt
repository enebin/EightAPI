package com.enebin

import com.enebin.plugins.DatabaseFactory
import io.ktor.server.application.*

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
