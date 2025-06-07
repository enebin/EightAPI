package com.enebin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors() {
    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)   // pre‑flight requests

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlRequestMethod)
        allowHeader(HttpHeaders.AccessControlRequestHeaders)

        allowCredentials = true

        // React 개발 서버를 허용
        allowHost("localhost:3000", schemes = listOf("http"))
        allowHost("localhost:5173", schemes = listOf("http"))   // Vite dev‑server
    }
}

/*
fun Application.configureCors() {
    // Allowed origins can be configured via application.conf or environment variable
    val allowedOrigins = environment.config
        .propertyOrNull("ktor.cors.allowed-hosts")
        ?.getString()
        ?.split(',')
        ?.map { it.trim() }
        ?.filter { it.isNotBlank() }
        ?: emptyList()

    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)   // pre‑flight requests

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlRequestMethod)
        allowHeader(HttpHeaders.AccessControlRequestHeaders)

        allowCredentials = true

        // Register all configured origins (sub‑domains included)
        allowedOrigins.forEach { host ->
            allowHost(host, subDomains = true, schemes = listOf("https", "http"))
        }

        // Fallback for local dev if no hosts configured
        if (allowedOrigins.isEmpty()) {
            allowHost("localhost:3000", schemes = listOf("http"))
            allowHost("localhost:5173", schemes = listOf("http"))
        }
    }
}
*/