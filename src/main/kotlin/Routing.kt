package com.enebin

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, Router!")
        }
        get("/json") {
            call.respond(Sample("Hello, JSON"))
        }
        get("/html") {
            call.respondText(
                """
        <html>
            <body>
                <h1>Hello Ktor HTML</h1>
                <form action="/json" method="get">
                    <button type="submit">Go to JSON</button>
                </form>
            </body>
        </html>
        """.trimIndent(),
                contentType = io.ktor.http.ContentType.Text.Html
            )
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}

@Serializable
data class Sample(val message: String, val id: Int = 1)