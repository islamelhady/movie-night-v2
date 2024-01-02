package com.elhady.movies.domain.usecases.search

import com.elhady.movies.data.repository.MovieRepository
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(name: String){
        return repository.insertSearchHistory(name)
    }
}