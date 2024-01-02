package com.elhady.movies.domain.usecases.search

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.search.SearchHistoryMapper
import com.elhady.movies.domain.models.SearchHistory
import javax.inject.Inject

class GetAllSearchHistoryUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val searchHistoryMapper: SearchHistoryMapper
) {
    suspend operator fun invoke(): List<SearchHistory> {
        return repository.getSearchHistory().map { searchHistoryMapper.map(it) }
    }
}