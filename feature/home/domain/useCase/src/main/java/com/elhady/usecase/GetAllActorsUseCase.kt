package com.elhady.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.entities.ActorEntity
import com.elhady.usecase.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllActorsUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorDtoMapper: ActorDtoMapper
) {
    suspend operator fun invoke(): Flow<PagingData<ActorEntity>> {
        return repository.getAllPopularPersons().flow.map { paging ->
            paging.map {
                actorDtoMapper.map(it)
            }
        }
    }
}