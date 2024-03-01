package com.elhady.viewmodel.mappers

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.models.PopularUiState
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