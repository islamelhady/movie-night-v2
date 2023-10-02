package com.elhady.movies.di

import com.elhady.movies.data.AppConfiguration
import com.elhady.movies.data.AppConfigurationImp
import com.elhady.movies.data.DataStorePreferences
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