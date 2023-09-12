package com.elhady.movies.di

import com.elhady.movies.data.DataClassParser
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.MovieRepositoryImp
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.AccountRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
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

    @Provides
    @Singleton
    fun provideAccountRepository(movieService: MovieService, dataClassParser: DataClassParser): AccountRepository{
        return AccountRepositoryImp(movieService, dataClassParser)
    }
}