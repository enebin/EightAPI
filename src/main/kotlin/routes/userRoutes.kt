// src/main/kotlin/routes/UserRoutes.kt

package routes

import dto.UserRequest
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import service.UserService

fun Routing.userRoutes() {
    route("/api/users") {
        // 사용자 생성 (회원가입)
        post {
            val request = call.receive<UserRequest>()
            UserService.createUser(request)
                .onSuccess { createdUser ->
                    call.respond(HttpStatusCode.Created, createdUser)
                }
                .onFailure { error ->
                    // 이메일 중복 시 409 Conflict 반환
                    val statusCode =
                        if (error is IllegalArgumentException)
                            HttpStatusCode.Conflict
                        else HttpStatusCode.InternalServerError

                    call.respond(statusCode, mapOf("error" to error.message))
                }
        }

        // 특정 사용자 조회
        get("/{id}") {
            val id =
                call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            val user = UserService.findUserById(id)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }
    }
}

