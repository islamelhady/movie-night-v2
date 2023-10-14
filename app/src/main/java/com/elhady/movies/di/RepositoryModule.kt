package com.elhady.movies.di

import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.DataClassParser
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.daos.SeriesDao
import com.elhady.movies.data.local.mappers.NowPlayingMovieMapper
import com.elhady.movies.data.local.mappers.TrendingMovieMapper
import com.elhady.movies.data.local.mappers.UpcomingMovieMapper
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.MovieRepositoryImp
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.AccountRepositoryImp
import com.elhady.movies.data.local.mappers.PopularMovieMapper
import com.elhady.movies.data.local.mappers.TopRatedMovieMapper
import com.elhady.movies.data.local.mappers.series.AiringTodaySeriesMapper
import com.elhady.movies.data.local.mappers.series.OnTheAirSeriesMapper
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.data.repository.SeriesRepositoryImp
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
    fun provideRepository(movieService: MovieService, popularMovieMapper: PopularMovieMapper, movieDao: MovieDao, appConfiguration: AppConfiguration, upcomingMovieMapper: UpcomingMovieMapper, trendingMovieMapper: TrendingMovieMapper, nowPlayingMovieMapper: NowPlayingMovieMapper, topRatedMovieMapper: TopRatedMovieMapper): MovieRepository{
        return MovieRepositoryImp(movieService,popularMovieMapper, movieDao, appConfiguration,trendingMovieMapper , upcomingMovieMapper, nowPlayingMovieMapper, topRatedMovieMapper)
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(movieService: MovieService, onTheAirSeriesMapper: OnTheAirSeriesMapper, airingSeriesMapper: AiringTodaySeriesMapper, seriesDao: SeriesDao, appConfiguration: AppConfiguration): SeriesRepository{
        return SeriesRepositoryImp(movieService,onTheAirSeriesMapper, airingSeriesMapper, seriesDao, appConfiguration)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(movieService: MovieService, dataClassParser: DataClassParser): AccountRepository{
        return AccountRepositoryImp(movieService, dataClassParser)
    }
}