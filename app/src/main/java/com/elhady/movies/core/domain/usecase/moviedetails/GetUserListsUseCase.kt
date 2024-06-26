package com.elhady.movies.core.domain.usecase.moviedetails

import com.elhady.movies.core.domain.entities.UserListEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetUserListsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): List<UserListEntity> {
        return movieRepository.getUserLists()
    }
}