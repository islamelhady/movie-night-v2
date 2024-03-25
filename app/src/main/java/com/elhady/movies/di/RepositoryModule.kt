package com.elhady.movies.di

import com.elhady.movies.core.data.repository.MovieRepositoryImpl
import com.elhady.movies.core.data.repository.WatchHistoryRepositoryImpl
import com.elhady.movies.core.data.repository.auth.AuthRepositoryImpl
import com.elhady.movies.core.domain.usecase.repository.AuthRepository
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import com.elhady.movies.core.domain.usecase.repository.WatchHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @ViewModelScoped abstract fun bindWatchHistoryRepository(watchHistoryRepositoryImpl: WatchHistoryRepositoryImpl): WatchHistoryRepository

}