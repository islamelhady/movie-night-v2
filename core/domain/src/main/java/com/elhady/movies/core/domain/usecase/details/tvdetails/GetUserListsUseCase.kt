package com.elhady.movies.core.domain.usecase.details.tvdetails

import com.elhady.movies.core.domain.model.UserListEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetUserListsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): List<UserListEntity> {
        return movieRepository.getUserLists()
    }
}
