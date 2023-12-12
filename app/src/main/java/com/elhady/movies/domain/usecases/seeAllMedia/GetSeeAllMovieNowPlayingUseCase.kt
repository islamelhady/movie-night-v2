package com.elhady.movies.domain.usecases.seeAllMedia

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSeeAllMovieNowPlayingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDtoMapper: MovieDtoMapper
) {
    operator fun invoke(): Flow<PagingData<Media>> {
        val data = movieRepository.getAllNowPlayingMovies()
        return data.flow.map { pagingData ->
            pagingData.map {
                movieDtoMapper.map(it)
            }
        }
    }
}