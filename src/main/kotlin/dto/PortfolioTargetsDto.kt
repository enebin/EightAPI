package dto

import kotlinx.serialization.Serializable

@Serializable
data class TargetAssetDto(
    val ticker: String,
    val assetName: String,
    val targetWeight: Double // 목표 비중 (%)
) {
    companion object {
        fun mock(): TargetAssetDto {
            return TargetAssetDto(
                ticker = "AAPL",
                assetName = "Apple Inc.",
                targetWeight = 30.0
            )
        }
    }
}

@Serializable
data class PortfolioTargetsDto(
    val assets: List<TargetAssetDto>
) {
    companion object {
        fun mock(): PortfolioTargetsDto {
            return PortfolioTargetsDto(
                assets = listOf(
                    TargetAssetDto.mock(),
                    TargetAssetDto(
                        ticker = "GOOGL",
                        assetName = "Alphabet Inc.",
                        targetWeight = 20.0
                    ),
                    TargetAssetDto(
                        ticker = "AMZN",
                        assetName = "Amazon.com Inc.",
                        targetWeight = 25.0
                    ),
                    TargetAssetDto(
                        ticker = "MSFT",
                        assetName = "Microsoft Corporation",
                        targetWeight = 25.0
                    ),
                    TargetAssetDto(
                        ticker = "TSLA",
                        assetName = "Tesla Inc.",
                        targetWeight = 30.0
                    )
                )
            )
        }
    }
}