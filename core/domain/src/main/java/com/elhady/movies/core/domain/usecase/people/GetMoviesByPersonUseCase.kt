package com.elhady.movies.core.domain.usecase.people

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.PeopleRepository
import javax.inject.Inject

class GetMoviesByPersonUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository,
) {
    suspend operator fun invoke(personId:Int): List<Movie>{
        return peopleRepository.getMoviesByPerson(personId)
    }
}
