package com.kampalahomes.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kampalahomes.core.model.session.Session
import com.kampalahomes.core.model.session.SessionMode
import com.kampalahomes.core.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kampala_homes_datastore")

@Singleton
class AppDataStore @Inject constructor(
    private val context: Context
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    val sessionFlow: Flow<Session> = context.dataStore.data
        .map { preferences ->
            Session(
                mode = SessionMode.valueOf(
                    preferences[SESSION_MODE_KEY] ?: SessionMode.GUEST.name
                ),
                userId = preferences[USER_ID_KEY],
                firstRunCompleted = preferences[FIRST_RUN_KEY] ?: false,
                hasLocationPermission = preferences[LOCATION_PERMISSION_KEY] ?: false,
                email = preferences[EMAIL_KEY],
                name = preferences[NAME_KEY]
            )
        }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .map { preferences ->
            UserPreferences(
                budgetMin = preferences[BUDGET_MIN_KEY],
                budgetMax = preferences[BUDGET_MAX_KEY],
                propertyTypes = preferences[PROPERTY_TYPES_KEY]?.let {
                    json.decodeFromString<List<String>>(it)
                } ?: emptyList(),
                preferredAreas = preferences[PREFERRED_AREAS_KEY]?.let {
                    json.decodeFromString<List<String>>(it)
                } ?: emptyList(),
                minBedrooms = preferences[MIN_BEDROOMS_KEY],
                minBathrooms = preferences[MIN_BATHROOMS_KEY],
                hasParking = preferences[HAS_PARKING_KEY],
                hasFurnishing = preferences[HAS_FURNISHING_KEY]
            )
        }

    suspend fun updateSession(session: Session) {
        context.dataStore.edit { preferences ->
            preferences[SESSION_MODE_KEY] = session.mode.name
            session.userId?.let { preferences[USER_ID_KEY] = it }
            preferences[FIRST_RUN_KEY] = session.firstRunCompleted
            preferences[LOCATION_PERMISSION_KEY] = session.hasLocationPermission
            session.email?.let { preferences[EMAIL_KEY] = it }
            session.name?.let { preferences[NAME_KEY] = it }
        }
    }

    suspend fun updateUserPreferences(preferences: UserPreferences) {
        context.dataStore.edit { prefs ->
            preferences.budgetMin?.let { prefs[BUDGET_MIN_KEY] = it }
            preferences.budgetMax?.let { prefs[BUDGET_MAX_KEY] = it }
            preferences.propertyTypes.takeIf { it.isNotEmpty() }?.let {
                prefs[PROPERTY_TYPES_KEY] = json.encodeToString(it)
            }
            preferences.preferredAreas.takeIf { it.isNotEmpty() }?.let {
                prefs[PREFERRED_AREAS_KEY] = json.encodeToString(it)
            }
            preferences.minBedrooms?.let { prefs[MIN_BEDROOMS_KEY] = it }
            preferences.minBathrooms?.let { prefs[MIN_BATHROOMS_KEY] = it }
            preferences.hasParking?.let { prefs[HAS_PARKING_KEY] = it }
            preferences.hasFurnishing?.let { prefs[HAS_FURNISHING_KEY] = it }
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val SESSION_MODE_KEY = stringPreferencesKey("session_mode")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val FIRST_RUN_KEY = booleanPreferencesKey("first_run_completed")
        private val LOCATION_PERMISSION_KEY = booleanPreferencesKey("location_permission")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val NAME_KEY = stringPreferencesKey("name")

        private val BUDGET_MIN_KEY = longPreferencesKey("budget_min")
        private val BUDGET_MAX_KEY = longPreferencesKey("budget_max")
        private val PROPERTY_TYPES_KEY = stringPreferencesKey("property_types")
        private val PREFERRED_AREAS_KEY = stringPreferencesKey("preferred_areas")
        private val MIN_BEDROOMS_KEY = intPreferencesKey("min_bedrooms")
        private val MIN_BATHROOMS_KEY = intPreferencesKey("min_bathrooms")
        private val HAS_PARKING_KEY = booleanPreferencesKey("has_parking")
        private val HAS_FURNISHING_KEY = booleanPreferencesKey("has_furnishing")
    }
}
