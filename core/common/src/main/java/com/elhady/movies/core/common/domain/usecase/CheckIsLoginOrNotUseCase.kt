package com.elhady.movies.core.common.domain.usecase

import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class CheckIsLoginOrNotUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Boolean {
        return movieRepository.isLoginOrNot()
    }
}
