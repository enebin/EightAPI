package com.enebin

import com.enebin.api.bitcoin.BitcoinApiClient
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(getBitcoinPrice())
        }

        get("/health") {
            call.respond(HealthResponse(status = "OK"))
        }

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}

private suspend fun getBitcoinPrice(): String {
    val priceIndex = BitcoinApiClient.fetchBitcoinPrice()
    BitcoinApiClient.close()
    return "비트코인 현재가: ${priceIndex.quotes.USD.price} USD\n" +
            "업데이트 시간: ${priceIndex.lastUpdated}"
}

@Serializable
data class HealthResponse(val status: String = "OK")