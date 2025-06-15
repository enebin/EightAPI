package com.enebin

import com.enebin.api.bitcoin.BitcoinApiClient
import dto.AccountSummaryResponse
import dto.HoldingsResponse
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import routes.healthRoutes
import routes.portfolioRoutes
import routes.userRoutes
import java.lang.Thread.sleep


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(getBitcoinPrice())
        }

        get("/api/account/summary") {
            call.respond(AccountSummaryResponse.mock())
        }

        get("/api/portfolio/holdings") {
            sleep(1000) // Simulate a delay for testing
            call.respond(HoldingsResponse.mock())
        }

        userRoutes()
        healthRoutes()
        portfolioRoutes()

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}

suspend fun getBitcoinPrice(): String {
    val priceIndex = BitcoinApiClient.fetchBitcoinPrice()
    BitcoinApiClient.close()
    return "비트코인 현재가: ${priceIndex.quotes.USD.price} USD\n" +
            "업데이트 시간: ${priceIndex.lastUpdated}"
}