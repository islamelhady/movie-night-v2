package com.elhady.movies.core.data.di

import com.elhady.movies.core.data.repository.AuthRepositoryImpl
import com.elhady.movies.core.data.repository.MovieRepositoryImpl
import com.elhady.movies.core.data.repository.WatchHistoryRepositoryImpl
import com.elhady.movies.core.domain.repository.AuthRepository
import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindWatchHistoryRepository(watchHistoryRepositoryImpl: WatchHistoryRepositoryImpl): WatchHistoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

}
