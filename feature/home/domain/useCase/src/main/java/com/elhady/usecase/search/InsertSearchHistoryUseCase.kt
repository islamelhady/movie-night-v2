package com.elhady.usecase.search

import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(name: String){
        return repository.insertSearchHistory(name)
    }
}