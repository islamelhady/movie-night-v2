package com.elhady.movies.di

import com.elhady.local.AppConfiguration
import com.elhady.local.AppConfigurationImp
import com.elhady.local.DataStorePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {

    @Provides
    @Singleton
    fun provideAppConfig(dataStorePreferences: DataStorePreferences): AppConfiguration {
        return AppConfigurationImp(dataStorePreferences)
    }
}