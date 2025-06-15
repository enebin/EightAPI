package routes

import dto.HealthResponse
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import plugins.DatabaseFactory.dbQuery

fun Routing.healthRoutes() {
    route("/api/health") {
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
}