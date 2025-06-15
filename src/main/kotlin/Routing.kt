package com.enebin

import com.enebin.api.bitcoin.BitcoinApiClient
import dto.AccountSummaryResponse
import dto.HealthResponse
import dto.HoldingsResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import plugins.DatabaseFactory.dbQuery
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

        route("/health") {
            get {
                call.respond(HealthResponse(status = "OK"))
            }

            get("/db") {
                try {
                    dbQuery {
                        // "SELECT 1" 쿼리를 실행
                        exec("SELECT 1")
                    }
                    call.respondText("데이터베이스 연결 성공!")
                } catch (e: Exception) {
                    call.respondText("데이터베이스 연결 실패: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }
        }

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