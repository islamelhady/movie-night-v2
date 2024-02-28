package com.elhady.usecase

import com.elhady.entities.ActorDetailsEntity
import com.elhady.usecase.repository.ActorRepository
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorDetailsMapper: ActorDetailsMapper
) {
    suspend operator fun invoke(actorID: Int): ActorDetailsEntity {
        val response = repository.getPersonsDetails(actorID = actorID)
        return response?.let {
            actorDetailsMapper.map(response)
        } ?: throw Throwable("Not success.")
    }
}