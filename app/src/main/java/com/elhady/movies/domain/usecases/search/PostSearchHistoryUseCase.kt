package com.elhady.movies.domain.usecases.search

import com.elhady.movies.data.local.database.entity.SearchHistoryEntity
import com.elhady.movies.data.repository.MovieRepository
import javax.inject.Inject

class PostSearchHistoryUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(id: Int, name: String){
        return repository.insertSearchItem(SearchHistoryEntity(
            id = id.toLong(),
            name = name
        ))
    }
}