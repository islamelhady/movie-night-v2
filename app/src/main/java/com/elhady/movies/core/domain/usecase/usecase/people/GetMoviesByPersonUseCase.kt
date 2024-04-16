package com.elhady.movies.core.domain.usecase.usecase.people

import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByPersonUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId:Int): List<MovieEntity>{
        return movieRepository.getMoviesByPerson(personId)
    }
}