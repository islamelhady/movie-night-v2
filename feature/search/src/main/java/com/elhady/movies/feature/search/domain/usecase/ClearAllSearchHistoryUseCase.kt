package com.elhady.movies.feature.search.domain.usecase

import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class ClearAllSearchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(){
        return movieRepository.clearAllSearchHistory()
    }
}
