package com.elhady.movies.domain.usecases.search

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchForMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val movieDtoMapper: MovieDtoMapper
) {
    operator fun invoke(movieQuery: String): Flow<PagingData<Media>> {
        return repository.searchForMoviesPager(query = movieQuery).flow.map { pagingData ->
            pagingData.map {
                movieDtoMapper.map(it)
            }
        }
    }
}