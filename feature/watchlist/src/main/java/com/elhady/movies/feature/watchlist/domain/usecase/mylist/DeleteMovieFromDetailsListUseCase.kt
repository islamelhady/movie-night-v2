package com.elhady.movies.feature.watchlist.domain.usecase.mylist

import com.elhady.movies.core.common.domain.entities.StatusEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieFromDetailsListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(listId: Int,mediaId: Int ): StatusEntity {
        return movieRepository.deleteMovieDetailsList(listId =listId , mediaId = mediaId )
    }
}
