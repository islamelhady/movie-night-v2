package com.elhady.movies.feature.details.domain.usecase.people

import com.elhady.movies.core.common.domain.entities.MovieEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByPersonUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId:Int): List<MovieEntity>{
        return movieRepository.getMoviesByPerson(personId)
    }
}
