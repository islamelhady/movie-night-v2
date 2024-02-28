package com.elhady.usecase.search

import com.elhady.entities.SearchHistoryEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetAllSearchHistoryUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val searchHistoryMapper: SearchHistoryMapper
) {
    suspend operator fun invoke(): List<SearchHistoryEntity> {
        return repository.getSearchHistory().map { searchHistoryMapper.map(it) }
    }
}