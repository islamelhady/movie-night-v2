package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.StatusResponse
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(listId: Int): StatusResponse{
        return repository.deleteList(listId)
    }
}