package dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountSummaryResponse(
    val accountName: String,
    val accountNumber: String,
    val totalAssets: Long,
    val totalPrincipal: Long,
    val totalProfitLoss: Long,
    val totalProfitLossRate: Double,
    val cashBalance: Long
) {
    companion object {
        fun mock(): AccountSummaryResponse {
            return AccountSummaryResponse(
                accountName = "나의 소중한 연금 계좌",
                accountNumber = "123-456-789",
                totalAssets = 1000000L,
                totalPrincipal = 800000L,
                totalProfitLoss = 200000L,
                totalProfitLossRate = 25.0,
                cashBalance = 500000L
            )
        }
    }
}