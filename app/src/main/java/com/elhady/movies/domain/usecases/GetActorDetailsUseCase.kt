package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.domain.mappers.actor.ActorDetailsMapper
import com.elhady.movies.domain.models.ActorDetails
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorDetailsMapper: ActorDetailsMapper
) {
    suspend operator fun invoke(actorID: Int): ActorDetails {
        val response = repository.getActorDetails(actorID = actorID)
        return if (response != null) {
            actorDetailsMapper.map(response)
        } else {
            throw Throwable("Not success.")
        }
    }
}