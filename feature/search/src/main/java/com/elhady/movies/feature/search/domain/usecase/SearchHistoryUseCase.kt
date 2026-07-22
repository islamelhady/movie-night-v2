package com.elhady.movies.feature.search.domain.usecase

import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(keyword: String): List<String> {
        return movieRepository.getSearchHistory(keyword = keyword).sorted()
    }
}
