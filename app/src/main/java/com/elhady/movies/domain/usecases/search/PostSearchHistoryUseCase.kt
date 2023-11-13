package com.elhady.movies.domain.usecases.search

import com.elhady.movies.data.local.database.entity.SearchHistoryEntity
import com.elhady.movies.data.repository.MovieRepository

class PostSearchHistoryUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(id: Int, name: String){
        return repository.insertSearchItem(SearchHistoryEntity(
            id = id,
            name = name
        ))
    }
}