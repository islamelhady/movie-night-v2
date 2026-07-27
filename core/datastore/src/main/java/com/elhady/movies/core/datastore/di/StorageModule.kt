package com.elhady.movies.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.elhady.movies.core.common.UserDataProvider
import com.elhady.movies.core.datastore.local.PreferenceStorage
import com.elhady.movies.core.datastore.local.PreferenceStorageIml
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    @Singleton
    abstract fun bindPreferenceStorage(preferenceStorageIml: PreferenceStorageIml): PreferenceStorage

    @Binds
    @Singleton
    abstract fun bindUserDataProvider(preferenceStorage: PreferenceStorage): UserDataProvider

    companion object {
        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create() {
                applicationContext.preferencesDataStoreFile("AppPrefStorage")
            }
        }
    }
}
