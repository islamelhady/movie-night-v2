package com.elhady.movies.core.domain.usecase.mylist

import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMyListDetailsByListIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(listId: Int = 0): List<MovieEntity> {
        return  movieRepository.getDetailsList(listId)
    }
}