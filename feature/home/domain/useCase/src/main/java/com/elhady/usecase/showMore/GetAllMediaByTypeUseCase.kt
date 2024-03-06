package com.elhady.usecase.showMore

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.ActorRepository
import com.elhady.usecase.repository.MovieRepository
import com.elhady.usecase.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMediaByTypeUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val movieDtoMapper: MovieDtoMapper,
    private val tvShowDtoMapper: TVShowDtoMapper,
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(type: ShowMoreType, actionId: Int =0): Flow<PagingData<MovieEntity>> {
        return when(type){
            ShowMoreType.UPCOMING_MOVIE -> wrapper(movieRepository::getAllUpcomingMovies ,movieDtoMapper::map)
            ShowMoreType.TRENDING_MOVIE -> wrapper(movieRepository::getAllTrendingMovies, movieDtoMapper::map)
            ShowMoreType.NOW_PLAYING_MOVIE -> wrapper(movieRepository::getAllNowPlayingMovies, movieDtoMapper::map)
            ShowMoreType.MYSTERY_MOVIE -> wrapper(movieRepository::getAllMysteryMovies, movieDtoMapper::map)
            ShowMoreType.ADVENTURE_MOVIE -> wrapper(movieRepository::getAllAdventureMovies, movieDtoMapper::map)
            ShowMoreType.LATEST_TV -> wrapper(seriesRepository::getAllLatestTV, tvShowDtoMapper::map)
            ShowMoreType.POPULAR_TV -> wrapper(seriesRepository::getAllPopularTV, tvShowDtoMapper::map)
            ShowMoreType.TOP_RATED_TV -> wrapper(seriesRepository::getAllTopRatedTV, tvShowDtoMapper::map)
            ShowMoreType.TOP_RATED_MOVIE -> wrapper(movieRepository::getAllTopRatedMovies, movieDtoMapper::map)
            ShowMoreType.ON_THE_AIR_TV -> wrapper(seriesRepository::getAllOnTheAirSeries, tvShowDtoMapper::map)
            ShowMoreType.ACTOR_MOVIES -> wrapper( { actorRepository.getAllActorMovies(actionId) }, movieDtoMapper::map)
        }
    }

    private suspend fun <T : Any> wrapper(
        data: suspend () -> Pager<Int, T>,
        mapper: (T) -> MovieEntity,
    ): Flow<PagingData<MovieEntity>> {
        return data().flow.map { pagingData ->
            pagingData.map {
                mapper(it)
            }
        }
    }
}