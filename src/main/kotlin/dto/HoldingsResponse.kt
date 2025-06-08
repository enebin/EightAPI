package dto

import kotlinx.serialization.Serializable

@Serializable
data class HoldingDto(
    val ticker: String,
    val assetName: String,
    val quantity: Int,
    val averagePrice: Double,
    val currentPrice: Double,
    val totalValue: Double,
    val profitLoss: Double,
    val profitLossRate: Double,
    val assetClass: String
)

@Serializable
data class HoldingsResponse(
    val holdings: List<HoldingDto>
) {
    companion object {
        fun mock(): HoldingsResponse {
            return HoldingsResponse(
                holdings = listOf(
                    HoldingDto(
                        ticker = "AAPL",
                        assetName = "Apple Inc.",
                        quantity = 10,
                        averagePrice = 150.0,
                        currentPrice = 175.0,
                        totalValue = 1750.0,
                        profitLoss = 250.0,
                        profitLossRate = 16.67,
                        assetClass = "Equity"
                    ),
                    HoldingDto(
                        ticker = "GOOGL",
                        assetName = "Alphabet Inc.",
                        quantity = 5,
                        averagePrice = 2800.0,
                        currentPrice = 2900.0,
                        totalValue = 14500.0,
                        profitLoss = 500.0,
                        profitLossRate = 3.57,
                        assetClass = "Equity"
                    )
                )
            )
        }
    }
}