// src/main/kotlin/service/UserService.kt

package service

import dto.UserRequest
import dto.UserResponse
import model.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import plugins.DatabaseFactory.dbQuery

object UserService {

    // 새로운 사용자 생성
    suspend fun createUser(request: UserRequest): Result<UserResponse> {
        // 이메일 중복 확인
        val existingUser = dbQuery {
            Users.selectAll().where { Users.email eq request.email }.singleOrNull()
        }
        if (existingUser != null) {
            return Result.failure(IllegalArgumentException("이미 사용 중인 이메일입니다."))
        }

        // 사용자 생성
        val newUser = dbQuery {
            val insertStatement = Users.insert {
                it[name] = request.name
                it[email] = request.email
            }
            insertStatement.resultedValues?.firstOrNull()?.let { rowToUser(it) }
        }

        return if (newUser != null) {
            Result.success(newUser)
        } else {
            Result.failure(Exception("사용자 생성에 실패했습니다."))
        }
    }

    // ID로 사용자 조회
    suspend fun findUserById(id: Int): UserResponse? {
        return dbQuery {
            Users.select { Users.id eq id }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    // DB 조회 결과를 UserResponse DTO로 변환
    private fun rowToUser(row: ResultRow): UserResponse = UserResponse(
        id = row[Users.id],
        name = row[Users.name],
        email = row[Users.email],
        createdAt = row[Users.createdAt].toString()
    )
}