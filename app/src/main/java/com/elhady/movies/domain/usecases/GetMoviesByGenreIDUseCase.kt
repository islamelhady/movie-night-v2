package com.elhady.movies.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesByGenreIDUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val movieDtoMapper: MovieDtoMapper
) {
    operator fun invoke(genreId: Int): Flow<PagingData<Media>> {
        return repository.getMoviesByGenre(genreId).flow.map { paging ->
            paging.map {
                movieDtoMapper.map(it)
            }
        }
    }
}