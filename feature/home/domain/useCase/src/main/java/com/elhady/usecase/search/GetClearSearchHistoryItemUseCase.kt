package com.elhady.usecase.search

import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetClearSearchHistoryItemUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(keyword: String) {
        repository.deleteSearchHistory(keyword)
    }
}