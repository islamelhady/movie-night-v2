package com.elhady.movies.core.domain.usecase.people

import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.repository.PeopleRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetPopularPeopleUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<People> {
        refreshIfNeededUseCase()
        return peopleRepository.getPopularPeopleFromDatabase()
            .also { if (it.isEmpty()) peopleRepository.refreshPopularPeople() }
            .take(limit)
    }
}
