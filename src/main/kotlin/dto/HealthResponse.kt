package dto

import kotlinx.serialization.Serializable

@Serializable
data class HealthResponse(val status: String = "OK")