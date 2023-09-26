package com.elhady.movies.ui.home

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.PopularMovie
import javax.inject.Inject

class PopularUiMapper @Inject constructor():Mapper<PopularMovie,PopularUiState> {
    override fun map(input: PopularMovie): PopularUiState {
        return PopularUiState(
            movieId = input.movieId,
            title = input.title,
            imageUrl = input.imageUrl,
            movieRate = input.movieRate,
        )
    }
}