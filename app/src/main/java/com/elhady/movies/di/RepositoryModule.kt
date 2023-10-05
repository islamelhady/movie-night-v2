package com.elhady.movies.di

import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.DataClassParser
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.mappers.NowPlayingMovieMapper
import com.elhady.movies.data.local.mappers.TrendingMovieMapper
import com.elhady.movies.data.local.mappers.UpcomingMovieMapper
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.MovieRepositoryImp
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.AccountRepositoryImp
import com.elhady.movies.data.local.mappers.MovieMapper
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
    fun provideRepository(movieService: MovieService, popularMovieMapper: MovieMapper, movieDao: MovieDao, appConfiguration: AppConfiguration, upcomingMovieMapper: UpcomingMovieMapper, trendingMovieMapper: TrendingMovieMapper, nowPlayingMovieMapper: NowPlayingMovieMapper): MovieRepository{
        return MovieRepositoryImp(movieService,popularMovieMapper, movieDao, appConfiguration,trendingMovieMapper , upcomingMovieMapper, nowPlayingMovieMapper)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(movieService: MovieService, dataClassParser: DataClassParser): AccountRepository{
        return AccountRepositoryImp(movieService, dataClassParser)
    }
}