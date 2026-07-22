package com.elhady.movies.feature.search.domain.usecase

import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(keyword: String){
        return movieRepository.insertSearchHistory(keyword)
    }
}
