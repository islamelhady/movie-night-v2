package com.elhady.movies.core.domain.usecase.usecase.tvdetails

import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class CreateUserListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(listName: String): StatusEntity {
        return movieRepository.createUserList(listName)
    }
}