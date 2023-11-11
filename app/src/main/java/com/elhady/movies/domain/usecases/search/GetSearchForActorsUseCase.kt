package com.elhady.movies.domain.usecases.search

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.domain.mappers.search.ActorsDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSearchForActorsUseCase constructor(
    private val repository: ActorRepository,
    private val actorsDtoMapper: ActorsDtoMapper
) {
    suspend operator fun invoke(query: String): Flow<PagingData<Media>> {
        return repository.searchForActors(query).flow.map { pagingData ->
            pagingData.map {
                actorsDtoMapper.map(it)
            }
        }
    }
}