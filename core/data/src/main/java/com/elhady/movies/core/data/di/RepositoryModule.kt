package com.elhady.movies.core.data.di

import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import com.elhady.movies.core.data.repository.MovieRepositoryImpl
import com.elhady.movies.core.data.repository.WatchHistoryRepositoryImpl
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
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @ViewModelScoped abstract fun bindWatchHistoryRepository(watchHistoryRepositoryImpl: WatchHistoryRepositoryImpl): WatchHistoryRepository

}
