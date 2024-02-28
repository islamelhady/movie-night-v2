package com.elhady.usecase

import com.elhady.entities.ActorMoviesEntity
import com.elhady.usecase.repository.ActorRepository
import javax.inject.Inject

class GetActorsMoviesUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorMoviesMapper: ActorMoviesMapper
) {
    suspend operator fun invoke(actorID: Int): List<ActorMoviesEntity> {
        val response = repository.getPersonMovies(actorID = actorID)
        return response?.cast?.let {
            actorMoviesMapper.map(it)
        }  ?: throw Throwable("not success")
    }
}