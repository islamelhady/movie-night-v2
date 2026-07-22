package com.elhady.movies.feature.watchlist.domain.usecase.mylist

import com.elhady.movies.core.common.domain.entities.mylist.ListCreatedEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetListsCreatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): List<ListCreatedEntity> {
        return movieRepository.getListCreated()
    }
}
