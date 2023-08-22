package com.elhady.movies.di

import com.elhady.movies.data.remote.repository.MovieRepository
import com.elhady.movies.data.remote.repository.MovieRepositoryImp
import com.elhady.movies.data.remote.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(movieService: MovieService): MovieRepository{
        return MovieRepositoryImp(movieService)
    }
}