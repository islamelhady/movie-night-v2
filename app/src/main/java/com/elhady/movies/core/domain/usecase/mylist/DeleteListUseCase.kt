package com.elhady.movies.core.domain.usecase.mylist

import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(listId: Int): StatusEntity {
        return movieRepository.deleteList(listId =listId)
    }
}
