package com.kampalahomes.core.data.repository

import com.kampalahomes.core.model.property.Property
import com.kampalahomes.core.model.session.Session
import com.kampalahomes.core.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getSession(): Flow<Session>
    suspend fun updateSession(session: Session)
    suspend fun clearSession()
}

interface UserPreferencesRepository {
    fun getPreferences(): Flow<UserPreferences>
    suspend fun updatePreferences(preferences: UserPreferences)
    suspend fun clearPreferences()
}

interface PropertyRepository {
    suspend fun getProperties(): List<Property>
    suspend fun getPropertyById(id: String): Property?
    suspend fun getSavedProperties(): List<Property>
    suspend fun saveProperty(propertyId: String)
    suspend fun unsaveProperty(propertyId: String)
    suspend fun searchProperties(
        query: String? = null,
        minPrice: Long? = null,
        maxPrice: Long? = null,
        bedrooms: Int? = null,
        propertyType: String? = null
    ): List<Property>
}