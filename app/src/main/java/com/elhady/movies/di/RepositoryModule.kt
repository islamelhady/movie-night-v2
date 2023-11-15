package com.elhady.movies.di

import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.DataClassParser
import com.elhady.movies.data.local.database.daos.ActorDao
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.daos.SeriesDao
import com.elhady.movies.data.local.mappers.actors.ActorsMapper
import com.elhady.movies.data.local.mappers.movies.AdventureMoviesMapper
import com.elhady.movies.data.local.mappers.movies.MysteryMoviesMapper
import com.elhady.movies.data.local.mappers.movies.NowPlayingMovieMapper
import com.elhady.movies.data.local.mappers.movies.TrendingMovieMapper
import com.elhady.movies.data.local.mappers.movies.UpcomingMovieMapper
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.MovieRepositoryImp
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.AccountRepositoryImp
import com.elhady.movies.data.local.mappers.movies.PopularMovieMapper
import com.elhady.movies.data.local.mappers.movies.TopRatedMovieMapper
import com.elhady.movies.data.local.mappers.series.AiringTodaySeriesMapper
import com.elhady.movies.data.local.mappers.series.OnTheAirSeriesMapper
import com.elhady.movies.data.local.mappers.series.TVSeriesListsMapper
import com.elhady.movies.data.repository.mediaDataSource.actors.ActorDataSource
import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.data.repository.ActorRepositoryImp
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.data.repository.SeriesRepositoryImp
import com.elhady.movies.data.repository.mediaDataSource.actors.ActorMoviesDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.AdventureMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.MovieDataSourceContainer
import com.elhady.movies.data.repository.mediaDataSource.movies.MysteryMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.NowPlayingMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.series.TopRatedTVDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.TrendingMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.UpcomingMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.series.LatestTVDataSource
import com.elhady.movies.data.repository.mediaDataSource.series.OnTheAirTVDataSource
import com.elhady.movies.data.repository.mediaDataSource.series.PopularTVDataSource
import com.elhady.movies.data.repository.mediaDataSource.series.SeriesDataSourceContainer
import com.elhady.movies.data.repository.searchDataSource.ActorsSearchDataSource
import com.elhady.movies.data.repository.searchDataSource.MovieSearchDataSource
import com.elhady.movies.data.repository.searchDataSource.SeriesSearchDataSource
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
    fun provideRepository(movieService: MovieService, popularMovieMapper: PopularMovieMapper, movieDao: MovieDao, appConfiguration: AppConfiguration, upcomingMovieMapper: UpcomingMovieMapper, trendingMovieMapper: TrendingMovieMapper, nowPlayingMovieMapper: NowPlayingMovieMapper, topRatedMovieMapper: TopRatedMovieMapper, mysteryMoviesMapper: MysteryMoviesMapper, adventureMoviesMapper: AdventureMoviesMapper, movieDataSourceContainer: MovieDataSourceContainer, movieSearchDataSource: MovieSearchDataSource): MovieRepository{
        return MovieRepositoryImp(movieService,popularMovieMapper, movieDao, appConfiguration,trendingMovieMapper , upcomingMovieMapper, nowPlayingMovieMapper, topRatedMovieMapper, mysteryMoviesMapper, adventureMoviesMapper, movieDataSourceContainer, movieSearchDataSource)
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(movieService: MovieService, onTheAirSeriesMapper: OnTheAirSeriesMapper, airingSeriesMapper: AiringTodaySeriesMapper, tvSeriesListsMapper: TVSeriesListsMapper, seriesDao: SeriesDao, appConfiguration: AppConfiguration,seriesDataSourceContainer: SeriesDataSourceContainer, seriesSearchDataSource: SeriesSearchDataSource): SeriesRepository{
        return SeriesRepositoryImp(movieService,onTheAirSeriesMapper, airingSeriesMapper,tvSeriesListsMapper , seriesDao, appConfiguration, seriesDataSourceContainer, seriesSearchDataSource)
    }

    @Provides
    @Singleton
    fun provideActorsRepository(service: MovieService,actorsMapper: ActorsMapper,actorDao: ActorDao, appConfiguration: AppConfiguration, actorDataSource: ActorDataSource, actorMoviesDataSource: ActorMoviesDataSource, actorsSearchDataSource: ActorsSearchDataSource): ActorRepository{
        return ActorRepositoryImp(service, actorsMapper, actorDao, appConfiguration, actorDataSource, actorMoviesDataSource, actorsSearchDataSource)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(movieService: MovieService, dataClassParser: DataClassParser, appConfiguration: AppConfiguration): AccountRepository{
        return AccountRepositoryImp(movieService, dataClassParser, appConfiguration)
    }
}