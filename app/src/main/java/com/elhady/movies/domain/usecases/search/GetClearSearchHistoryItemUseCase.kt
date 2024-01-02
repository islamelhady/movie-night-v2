package com.elhady.movies.domain.usecases.search

import com.elhady.movies.data.repository.MovieRepository
import javax.inject.Inject

class GetClearSearchHistoryItemUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(keyword: String) {
        repository.deleteSearchHistory(keyword)
    }
}