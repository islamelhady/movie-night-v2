package com.elhady.usecase.showMore.actor

import androidx.paging.PagingData
import com.elhady.entities.ActorEntity
import com.elhady.usecase.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMorePopularActorByTypeUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(): Flow<PagingData<ActorEntity>> {
        return actorRepository.getPopularPersonsPaging().flow
    }
}