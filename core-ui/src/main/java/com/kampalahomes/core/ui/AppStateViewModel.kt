package com.kampalahomes.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kampalahomes.core.data.repository.SessionRepository
import com.kampalahomes.core.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.getSession().collect { session ->
                _appState.value = _appState.value.copy(
                    sessionMode = when (session.mode.name) {
                        "AUTHENTICATED" -> SessionMode.AUTHENTICATED
                        else -> SessionMode.GUEST
                    },
                    hasCompletedFirstRun = session.firstRunCompleted,
                    hasLocationPermission = session.hasLocationPermission
                )
            }
        }
    }

    fun updateSessionMode(sessionMode: SessionMode) {
        viewModelScope.launch {
            val currentSession = _appState.value
            sessionRepository.updateSession(
                com.kampalahomes.core.model.session.Session(
                    mode = when (sessionMode) {
                        SessionMode.GUEST -> com.kampalahomes.core.model.session.SessionMode.GUEST
                        SessionMode.AUTHENTICATED -> com.kampalahomes.core.model.session.SessionMode.AUTHENTICATED
                    },
                    firstRunCompleted = currentSession.hasCompletedFirstRun,
                    hasLocationPermission = currentSession.hasLocationPermission
                )
            )
        }
    }

    fun setFirstRunCompleted(completed: Boolean) {
        viewModelScope.launch {
            val currentSession = _appState.value
            sessionRepository.updateSession(
                com.kampalahomes.core.model.session.Session(
                    mode = when (currentSession.sessionMode) {
                        SessionMode.GUEST -> com.kampalahomes.core.model.session.SessionMode.GUEST
                        SessionMode.AUTHENTICATED -> com.kampalahomes.core.model.session.SessionMode.AUTHENTICATED
                    },
                    firstRunCompleted = completed,
                    hasLocationPermission = currentSession.hasLocationPermission
                )
            )
            _appState.value = _appState.value.copy(hasCompletedFirstRun = completed)
        }
    }

    fun setLocationPermission(granted: Boolean) {
        viewModelScope.launch {
            val currentSession = _appState.value
            sessionRepository.updateSession(
                com.kampalahomes.core.model.session.Session(
                    mode = when (currentSession.sessionMode) {
                        SessionMode.GUEST -> com.kampalahomes.core.model.session.SessionMode.GUEST
                        SessionMode.AUTHENTICATED -> com.kampalahomes.core.model.session.SessionMode.AUTHENTICATED
                    },
                    firstRunCompleted = currentSession.hasCompletedFirstRun,
                    hasLocationPermission = granted
                )
            )
            _appState.value = _appState.value.copy(hasLocationPermission = granted)
        }
    }
}
