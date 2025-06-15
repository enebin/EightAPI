// src/main/kotlin/dto/UserDto.kt
package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name: String,
    val email: String
)

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val createdAt: String
)