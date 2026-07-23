package com.elhady.movies.core.domain.usecase.details.tvdetails

import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class CreateUserListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(listName: String): StatusEntity {
        return movieRepository.createUserList(listName)
    }
}
