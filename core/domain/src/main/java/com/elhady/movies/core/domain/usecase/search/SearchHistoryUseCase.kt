package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.repository.SearchRepository
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    suspend operator fun invoke(keyword: String): List<String> {
        return searchRepository.getSearchHistory(keyword = keyword).sorted()
    }
}
