package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.repository.SearchRepository
import javax.inject.Inject

class ClearAllSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(){
        return searchRepository.clearAllSearchHistory()
    }
}
