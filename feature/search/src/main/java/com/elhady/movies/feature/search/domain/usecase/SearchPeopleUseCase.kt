package com.elhady.movies.feature.search.domain.usecase

import com.elhady.movies.core.common.domain.entities.PeopleEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(keyword: String): List<PeopleEntity>{
        return movieRepository.searchForPeople(keyword)
    }
}
