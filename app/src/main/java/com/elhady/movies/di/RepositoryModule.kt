package com.elhady.movies.di

import com.elhady.repository.AccountRepositoryImp
import com.elhady.repository.ActorRepositoryImp
import com.elhady.repository.MovieRepositoryImp
import com.elhady.repository.SeriesRepositoryImp
import com.elhady.usecase.repository.AccountRepository
import com.elhady.usecase.repository.ActorRepository
import com.elhady.usecase.repository.MovieRepository
import com.elhady.usecase.repository.SeriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideMovieRepository(movieRepositoryImp: MovieRepositoryImp): MovieRepository


    @Binds
    @ViewModelScoped
    abstract fun bindSeriesRepository(seriesRepositoryImp: SeriesRepositoryImp): SeriesRepository


    @Binds
    @ViewModelScoped
    abstract fun bindActorRepository(actorRepositoryImp: ActorRepositoryImp): ActorRepository


    @Binds
    @ViewModelScoped
    abstract fun bindAccountRepository(accountRepositoryImp: AccountRepositoryImp): AccountRepository
}