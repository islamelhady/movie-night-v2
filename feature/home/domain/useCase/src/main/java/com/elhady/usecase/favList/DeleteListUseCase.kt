package com.elhady.usecase.favList

import com.elhady.entities.StatusResponseEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(listId: Int): StatusResponseEntity{
        return repository.deleteList(listId)
    }
}