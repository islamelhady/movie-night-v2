package com.elhady.viewmodel.home.mappers

import com.elhady.entities.PopularMovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.PopularMoviesUiState
import javax.inject.Inject

class PopularMovieUiMapper @Inject constructor(): Mapper<PopularMovieEntity, PopularMoviesUiState> {
    override fun map(input: PopularMovieEntity): PopularMoviesUiState {
        return PopularMoviesUiState(
            id = input.movieId,
            imageUrl = input.movieImage,
            title = input.movieName,
            rate = input.movieRate,
            genre = input.movieGenre
        )
    }
}