package com.elhady.movies.core.domain.usecase.searchhistory

import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(keyword: String): List<String> {
        return movieRepository.getSearchHistory(keyword = keyword).sorted()
    }
}