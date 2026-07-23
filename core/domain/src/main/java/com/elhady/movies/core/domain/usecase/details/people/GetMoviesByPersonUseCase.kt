package com.elhady.movies.core.domain.usecase.details.people

import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByPersonUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId:Int): List<MovieEntity>{
        return movieRepository.getMoviesByPerson(personId)
    }
}
