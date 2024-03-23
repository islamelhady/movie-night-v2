package com.elhady.movies.core.domain.usecase.usecase.common

import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class AddToFavouriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        mediaType: String,
        isFavorite: Boolean = true
    ): StatusEntity {
        return movieRepository.addFavouriteList(movieId, mediaType, isFavorite)
    }
}