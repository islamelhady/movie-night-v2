package com.elhady.movies.core.domain.usecase.common

import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class CheckIsLoginOrNotUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Boolean {
        return movieRepository.isLoginOrNot()
    }
}