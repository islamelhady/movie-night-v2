package com.elhady.movies.core.domain.usecase.people

import com.elhady.movies.core.domain.model.people.PeopleDetails
import com.elhady.movies.core.domain.repository.PeopleRepository
import javax.inject.Inject

class GetPeopleDetailsUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository,
) {
    suspend operator fun invoke(personId:Int): PeopleDetails {
        return peopleRepository.getPersonDetails(personId)
    }
}
