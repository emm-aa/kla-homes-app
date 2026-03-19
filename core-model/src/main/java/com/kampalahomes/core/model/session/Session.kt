package com.kampalahomes.core.model.session

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val mode: SessionMode,
    val userId: String? = null,
    val firstRunCompleted: Boolean = false,
    val hasLocationPermission: Boolean = false,
    val email: String? = null,
    val name: String? = null
)

enum class SessionMode {
    GUEST,
    AUTHENTICATED
}