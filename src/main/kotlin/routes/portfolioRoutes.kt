// src/main/kotlin/routes/PortfolioRoutes.kt

package routes

import dto.PortfolioTargetsDto
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import service.PortfolioService

private const val PORTFOLIO_TARGETS_PATH = "/api/portfolio/"

fun Routing.portfolioRoutes() {
    route("$PORTFOLIO_TARGETS_PATH/{userId}") {
        get {
            // ✅ URL 경로에서 userId를 가져옵니다.
            val userId = call.parameters["userId"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid User ID"
            )

            // 서비스 호출 시 userId 전달
            val targets = PortfolioService.getTargets(userId)
            call.respond(HttpStatusCode.OK, targets)
        }

        post {
            // ✅ URL 경로에서 userId를 가져옵니다.
            val userId = call.parameters["userId"]?.toIntOrNull() ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                "Invalid User ID"
            )

            val request = call.receive<PortfolioTargetsDto>()

            PortfolioService.saveTargets(userId, request)
                .onSuccess { savedTargets ->
                    call.respond(HttpStatusCode.OK, savedTargets)
                }
                .onFailure { error ->
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to error.message))
                }
        }
    }
}