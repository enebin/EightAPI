// src/main/kotlin/model/PortfolioTarget.kt

package model

import org.jetbrains.exposed.sql.Table

object PortfolioTargets : Table("portfolio_targets") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id") // TODO: 추후 실제 사용자 인증 시스템과 연동
    val ticker = varchar("ticker", 10)
    val assetName = varchar("asset_name", 50)
    val targetWeight = double("target_weight")

    override val primaryKey = PrimaryKey(id)
}