package com.elhady.movies.core.domain.usecase.tvdetails

import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class AddToUserListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(listId: Int, mediaId: Int): StatusEntity {
        return movieRepository.postUserLists(listId, mediaId)
    }
}