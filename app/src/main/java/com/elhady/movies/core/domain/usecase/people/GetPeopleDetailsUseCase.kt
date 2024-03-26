package com.elhady.movies.core.domain.usecase.people

import com.elhady.movies.core.domain.entities.PeopleDetailsEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetPeopleDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId:Int): PeopleDetailsEntity {
        return movieRepository.getPersonDetails(personId)
    }
}