package com.elhady.movies.ui.mappers

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.PopularMovie
import com.elhady.movies.ui.models.PopularUiState
import javax.inject.Inject

class PopularUiMapper @Inject constructor(): Mapper<PopularMovie, PopularUiState> {
    override fun map(input: PopularMovie): PopularUiState {
        return PopularUiState(
            movieId = input.movieId,
            imageUrl = input.imageUrl,
            title = input.title,
            movieRate = input.movieRate,
            genre = input.genre
        )
    }
}