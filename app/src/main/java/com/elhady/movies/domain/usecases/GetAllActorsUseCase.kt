package com.elhady.movies.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.domain.mappers.actor.ActorDtoMapper
import com.elhady.movies.domain.models.Actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllActorsUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorDtoMapper: ActorDtoMapper
) {
    suspend operator fun invoke(): Flow<PagingData<Actor>> {
        return repository.getAllPopularPersons().flow.map { paging ->
            paging.map {
                actorDtoMapper.map(it)
            }
        }
    }
}