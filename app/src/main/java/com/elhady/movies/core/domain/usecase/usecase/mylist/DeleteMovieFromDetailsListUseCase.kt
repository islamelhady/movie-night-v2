package com.elhady.movies.core.domain.usecase.usecase.mylist

import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieFromDetailsListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(listId: Int,mediaId: Int ): StatusEntity {
        return movieRepository.deleteMovieDetailsList(listId =listId , mediaId = mediaId )
    }
}