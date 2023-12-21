package com.elhady.movies.domain.usecases.seeAllMedia

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.AllMediaType
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.mappers.series.TVShowDtoMapper
import com.elhady.movies.domain.models.Media
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
    suspend operator fun invoke(type: SeeAllType, actionId: Int =0): Flow<PagingData<Media>> {
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
            SeeAllType.POPULAR_PEOPLE -> TODO()
        }
    }

    private suspend fun <T : Any> wrapper(
        data: suspend () -> Pager<Int, T>,
        mapper: (T) -> Media,
    ): Flow<PagingData<Media>> {
        return data().flow.map { pagingData ->
            pagingData.map {
                mapper(it)
            }
        }
    }
}