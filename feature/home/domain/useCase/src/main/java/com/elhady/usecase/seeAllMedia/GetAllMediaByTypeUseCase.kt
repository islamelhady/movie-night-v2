package com.elhady.usecase.seeAllMedia

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.entities.MediaEntity
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
    suspend operator fun invoke(type: SeeAllType, actionId: Int =0): Flow<PagingData<MediaEntity>> {
        return when(type){
            SeeAllType.UPCOMING_MOVIE -> wrapper(movieRepository::getAllUpcomingMovies ,movieDtoMapper::map)
            SeeAllType.TRENDING_MOVIE -> wrapper(movieRepository::getAllTrendingMovies, movieDtoMapper::map)
            SeeAllType.NOW_PLAYING_MOVIE -> wrapper(movieRepository::getAllNowPlayingMovies, movieDtoMapper::map)
            SeeAllType.MYSTERY_MOVIE -> wrapper(movieRepository::getAllMysteryMovies, movieDtoMapper::map)
            SeeAllType.ADVENTURE_MOVIE -> wrapper(movieRepository::getAllAdventureMovies, movieDtoMapper::map)
            SeeAllType.LATEST_TV -> wrapper(seriesRepository::getAllLatestTV, tvShowDtoMapper::map)
            SeeAllType.POPULAR_TV -> wrapper(seriesRepository::getAllPopularTV, tvShowDtoMapper::map)
            SeeAllType.TOP_RATED_TV -> wrapper(seriesRepository::getAllTopRatedTV, tvShowDtoMapper::map)
            SeeAllType.TOP_RATED_MOVIE -> wrapper(movieRepository::getAllTopRatedMovies, movieDtoMapper::map)
            SeeAllType.ON_THE_AIR_TV -> wrapper(seriesRepository::getAllOnTheAirSeries, tvShowDtoMapper::map)
            SeeAllType.ACTOR_MOVIES -> wrapper( { actorRepository.getAllActorMovies(actionId) }, movieDtoMapper::map)
        }
    }

    private suspend fun <T : Any> wrapper(
        data: suspend () -> Pager<Int, T>,
        mapper: (T) -> MediaEntity,
    ): Flow<PagingData<MediaEntity>> {
        return data().flow.map { pagingData ->
            pagingData.map {
                mapper(it)
            }
        }
    }
}