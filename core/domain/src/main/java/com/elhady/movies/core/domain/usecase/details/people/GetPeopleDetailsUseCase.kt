package com.elhady.movies.core.domain.usecase.details.people

import com.elhady.movies.core.domain.model.PeopleDetailsEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetPeopleDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId:Int): PeopleDetailsEntity {
        return movieRepository.getPersonDetails(personId)
    }
}
