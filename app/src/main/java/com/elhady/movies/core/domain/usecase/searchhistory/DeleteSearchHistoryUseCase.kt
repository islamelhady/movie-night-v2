package com.elhady.movies.core.domain.usecase.searchhistory

import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteSearchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(keyword: String){
        return movieRepository.deleteSearchHistory(keyword = keyword)
    }
}