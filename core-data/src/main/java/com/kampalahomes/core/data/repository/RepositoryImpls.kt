package com.kampalahomes.core.data.repository

import com.kampalahomes.core.data.datastore.AppDataStore
import com.kampalahomes.core.model.session.Session
import com.kampalahomes.core.model.session.SessionMode
import com.kampalahomes.core.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    private val dataStore: AppDataStore
) : SessionRepository {
    override fun getSession(): Flow<Session> {
        return dataStore.sessionFlow
    }

    override suspend fun updateSession(session: Session) {
        dataStore.updateSession(session)
    }

    override suspend fun clearSession() {
        dataStore.updateSession(
            Session(
                mode = SessionMode.GUEST,
                userId = null,
                firstRunCompleted = false,
                hasLocationPermission = false,
                email = null,
                name = null
            )
        )
    }
}

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: AppDataStore
) : UserPreferencesRepository {
    override fun getPreferences(): Flow<UserPreferences> {
        return dataStore.userPreferencesFlow
    }

    override suspend fun updatePreferences(preferences: UserPreferences) {
        dataStore.updateUserPreferences(preferences)
    }

    override suspend fun clearPreferences() {
        dataStore.updateUserPreferences(UserPreferences())
    }
}
