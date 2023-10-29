package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.domain.mappers.ActorMoviesMapper
import com.elhady.movies.domain.mappers.ListMapper
import com.elhady.movies.domain.models.ActorMovies
import javax.inject.Inject

class GetActorsMoviesUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorMoviesMapper: ActorMoviesMapper
) {
    suspend operator fun invoke(actorID: Int): List<ActorMovies> {
        val response = repository.getPersonMovies(actorID = actorID)
        return ListMapper(actorMoviesMapper).mapList(response?.cast)
    }
}