package com.elhady.movies.core.domain.usecase.details.people

import com.elhady.movies.core.domain.model.PeopleDetailsEntity
import com.elhady.movies.core.domain.repository.PeopleRepository
import javax.inject.Inject

class GetPeopleDetailsUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository,
) {
    suspend operator fun invoke(personId:Int): PeopleDetailsEntity {
        return peopleRepository.getPersonDetails(personId)
    }
}
