package com.elhady.movies.domain.usecases.search

import androidx.paging.PagingData
import com.elhady.movies.data.local.mappers.actors.ActorsMapper
import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSearchForActorsUseCase constructor(private val repository: ActorRepository, private val mediamap) {
    suspend operator fun invoke(query: String): Flow<PagingData<Media>>{
        repository.searchForActors(query).flow.map {

        }
    }
}