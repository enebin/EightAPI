package com.enebin.api.bitcoin

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object BitcoinApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun fetchBitcoinPrice(): CoinTicker {
        val url = "https://api.coinpaprika.com/v1/tickers/btc-bitcoin"
        return client.get(url).body()
    }

    fun close() {
        client.close()
    }
}