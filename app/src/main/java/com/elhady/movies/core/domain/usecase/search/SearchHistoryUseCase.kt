package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<String> {
        return movieRepository.searchHistory()
    }
}