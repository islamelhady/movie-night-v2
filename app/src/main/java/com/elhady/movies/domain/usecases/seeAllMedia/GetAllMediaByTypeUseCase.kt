package com.elhady.movies.domain.usecases.seeAllMedia

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.enums.AllMediaType
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMediaByTypeUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val movieDtoMapper: MovieDtoMapper
) {
    suspend operator fun invoke(type: AllMediaType): Flow<PagingData<Media>> {
        return when(type){
            AllMediaType.UPCOMING -> wrapper(repository::getAllUpcomingMovies ,movieDtoMapper::map)
            AllMediaType.TRENDING -> wrapper(repository::getAllTrendingMovies, movieDtoMapper::map)
            AllMediaType.NOW_PLAYING -> wrapper(repository::getAllNowPlayingMovies, movieDtoMapper::map)
            AllMediaType.MYSTERY -> wrapper(repository::getAllMysteryMovies, movieDtoMapper::map)
            AllMediaType.ADVENTURE -> TODO()
            AllMediaType.LATEST -> TODO()
            AllMediaType.POPULAR -> TODO()
            AllMediaType.TOP_RATED -> TODO()
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