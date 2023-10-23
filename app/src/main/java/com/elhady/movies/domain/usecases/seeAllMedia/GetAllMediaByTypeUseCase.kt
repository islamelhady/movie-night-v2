package com.elhady.movies.domain.usecases.seeAllMedia

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.AllMediaType
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
    private val tvShowDtoMapper: TVShowDtoMapper
) {
    suspend operator fun invoke(type: AllMediaType): Flow<PagingData<Media>> {
        return when(type){
            AllMediaType.UPCOMING -> wrapper(movieRepository::getAllUpcomingMovies ,movieDtoMapper::map)
            AllMediaType.TRENDING -> wrapper(movieRepository::getAllTrendingMovies, movieDtoMapper::map)
            AllMediaType.NOW_PLAYING -> wrapper(movieRepository::getAllNowPlayingMovies, movieDtoMapper::map)
            AllMediaType.MYSTERY -> wrapper(movieRepository::getAllMysteryMovies, movieDtoMapper::map)
            AllMediaType.ADVENTURE -> wrapper(movieRepository::getAllAdventureMovies, movieDtoMapper::map)
            AllMediaType.LATEST -> wrapper(seriesRepository::getAllLatestTV, tvShowDtoMapper::map)
            AllMediaType.POPULAR -> wrapper(seriesRepository::getAllPopularTV, tvShowDtoMapper::map)
            AllMediaType.TOP_RATED -> wrapper(seriesRepository::getAllTopRatedTV, tvShowDtoMapper::map)
            AllMediaType.ON_THE_AIR -> wrapper(seriesRepository::getAllTopRatedTV, tvShowDtoMapper::map)
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