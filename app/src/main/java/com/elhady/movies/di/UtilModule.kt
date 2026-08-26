package com.elhady.movies.di

import com.elhady.movies.core.data.utils.RealClock
import com.elhady.movies.core.domain.utils.Clock
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Random
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UtilModule {

    @Binds
    @Singleton
    abstract fun bindClock(realClock: RealClock): Clock

    companion object {
        @Provides
        @Singleton
        fun provideRandomClass(): Random {
            return Random()
        }
    }
}
