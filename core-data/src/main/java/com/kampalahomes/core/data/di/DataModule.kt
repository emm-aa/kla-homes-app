package com.kampalahomes.core.data.di

import android.content.Context
import com.kampalahomes.core.data.datastore.AppDataStore
import com.kampalahomes.core.data.repository.PropertyRepositoryImpl
import com.kampalahomes.core.data.repository.SessionRepository
import com.kampalahomes.core.data.repository.SessionRepositoryImpl
import com.kampalahomes.core.data.repository.UserPreferencesRepository
import com.kampalahomes.core.data.repository.UserPreferencesRepositoryImpl
import com.kampalahomes.core.data.repository.PropertyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context): AppDataStore {
        return AppDataStore(context)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(dataStore: AppDataStore): SessionRepository {
        return SessionRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: AppDataStore): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun providePropertyRepository(): PropertyRepository {
        return PropertyRepositoryImpl()
    }
}