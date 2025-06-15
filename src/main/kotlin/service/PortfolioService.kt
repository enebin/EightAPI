// src/main/kotlin/service/PortfolioService.kt

package service

import dto.PortfolioTargetsDto
import dto.TargetAssetDto
import model.PortfolioTargets
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import plugins.DatabaseFactory.dbQuery

object PortfolioService {

    private const val TOLERANCE = 0.001 // 부동소수점 비교를 위한 허용 오차

    // 특정 사용자의 목표 비중 조회
    suspend fun getTargets(userId: Int): PortfolioTargetsDto {
        val assets = dbQuery {
            PortfolioTargets.select { PortfolioTargets.userId eq userId }
                .map { row ->
                    TargetAssetDto(
                        ticker = row[PortfolioTargets.ticker],
                        assetName = row[PortfolioTargets.assetName],
                        targetWeight = row[PortfolioTargets.targetWeight]
                    )
                }
        }
        return PortfolioTargetsDto(assets = assets)
    }

    // 목표 비중 저장 (기존 데이터는 삭제 후 새로 삽입)
    suspend fun saveTargets(userId: Int, targets: PortfolioTargetsDto): Result<PortfolioTargetsDto> {
        // 유효성 검증: 비중의 합이 100인지 확인
        val totalWeight = targets.assets.sumOf { it.targetWeight }
        if (kotlin.math.abs(totalWeight - 100.0) > TOLERANCE) {
            return Result.failure(IllegalArgumentException("목표 비중의 총합은 100이어야 합니다."))
        }

        dbQuery {
            // 이 사용자의 기존 목표 비중을 모두 삭제
            PortfolioTargets.deleteWhere { PortfolioTargets.userId eq userId }

            // 새로운 목표 비중을 하나씩 삽입
            targets.assets.forEach { asset ->
                PortfolioTargets.insert {
                    it[PortfolioTargets.userId] = userId
                    it[ticker] = asset.ticker
                    it[assetName] = asset.assetName
                    it[targetWeight] = asset.targetWeight
                }
            }
        }

        return Result.success(targets)
    }
}