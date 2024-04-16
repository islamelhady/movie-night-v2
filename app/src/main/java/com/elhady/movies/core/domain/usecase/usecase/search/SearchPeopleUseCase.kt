package com.elhady.movies.core.domain.usecase.usecase.search

import com.elhady.movies.core.domain.entities.PeopleEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(keyword: String): List<PeopleEntity>{
        return movieRepository.searchForPeople(keyword)
    }
}