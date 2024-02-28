package com.elhady.usecase.search

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchForMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val movieDtoMapper: MovieDtoMapper
) {
    suspend operator fun invoke(movieQuery: String): Flow<PagingData<MediaEntity>> {
        return repository.searchForMoviesPager(query = movieQuery).flow.map { pagingData ->
            pagingData.map {
                movieDtoMapper.map(it)
            }
        }
    }
}