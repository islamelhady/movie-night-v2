package com.elhady.movies.core.data.di

import com.elhady.movies.core.data.repository.AccountRepositoryImpl
import com.elhady.movies.core.data.repository.AuthRepositoryImpl
import com.elhady.movies.core.data.repository.GenreRepositoryImpl
import com.elhady.movies.core.data.repository.MovieRepositoryImpl
import com.elhady.movies.core.data.repository.PeopleRepositoryImpl
import com.elhady.movies.core.data.repository.SearchRepositoryImpl
import com.elhady.movies.core.data.repository.TvShowRepositoryImpl
import com.elhady.movies.core.data.repository.WatchHistoryRepositoryImpl
import com.elhady.movies.core.domain.repository.AccountRepository
import com.elhady.movies.core.domain.repository.AuthRepository
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.repository.PeopleRepository
import com.elhady.movies.core.domain.repository.SearchRepository
import com.elhady.movies.core.domain.repository.TvShowRepository
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
    abstract fun bindTvShowRepository(tvShowRepositoryImpl: TvShowRepositoryImpl): TvShowRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindPeopleRepository(peopleRepositoryImpl: PeopleRepositoryImpl): PeopleRepository

    @Binds
    @Singleton
    abstract fun bindGenreRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository

    @Binds
    @Singleton
    abstract fun bindWatchHistoryRepository(watchHistoryRepositoryImpl: WatchHistoryRepositoryImpl): WatchHistoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

}
