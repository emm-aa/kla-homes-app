package com.kampalahomes.core.ui

enum class SessionMode {
    GUEST,
    AUTHENTICATED
}

data class AppState(
    val sessionMode: SessionMode = SessionMode.GUEST,
    val hasCompletedFirstRun: Boolean = false,
    val hasLocationPermission: Boolean = false
)
