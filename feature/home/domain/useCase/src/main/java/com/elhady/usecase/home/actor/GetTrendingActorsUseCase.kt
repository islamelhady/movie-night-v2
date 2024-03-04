package com.elhady.usecase.home.actor

import com.elhady.entities.ActorEntity
import com.elhady.usecase.repository.ActorRepository
import javax.inject.Inject

class GetTrendingActorsUseCase @Inject constructor(
    private val repository: ActorRepository,
) {
    suspend operator fun invoke(limit: Int = 10): List<ActorEntity> {
        return repository.getPopularActorFromDatabase()
            .also { if (it.isEmpty()) repository.refreshPopularActor() }
            .take(limit)
    }
}