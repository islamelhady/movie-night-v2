package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.repository.SearchRepository
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(keyword: String): List<People>{
        return searchRepository.searchForPeople(keyword)
    }
}
