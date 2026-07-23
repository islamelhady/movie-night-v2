package com.elhady.movies.core.domain.usecase.home

import com.elhady.movies.core.domain.model.PeopleEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetPopularPeopleUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<PeopleEntity> {
        refreshIfNeededUseCase()
        return movieRepository.getPopularPeopleFromDatabase()
            .also { if (it.isEmpty()) movieRepository.refreshPopularPeople() }
            .take(limit)
    }
}
