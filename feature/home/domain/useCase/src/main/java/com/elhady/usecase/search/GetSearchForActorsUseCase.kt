package com.elhady.usecase.search

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchForActorsUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorsDtoMapper: ActorsDtoMapper
) {
    suspend operator fun invoke(query: String): Flow<PagingData<MediaEntity>> {
        return repository.searchForActors(query).flow.map { pagingData ->
            pagingData.map {
                actorsDtoMapper.map(it)
            }
        }
    }
}