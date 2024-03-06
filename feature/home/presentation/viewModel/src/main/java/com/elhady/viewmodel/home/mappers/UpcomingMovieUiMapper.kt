package com.elhady.viewmodel.home.mappers

import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.MoviesUiState
import javax.inject.Inject

class UpcomingMovieUiMapper @Inject constructor() : Mapper<MovieEntity, MoviesUiState> {
    override fun map(input: MovieEntity): MoviesUiState {
        return MoviesUiState(
            id = input.movieId,
            imageUrl = input.movieImage,
            rate = input.movieRate
        )
    }
}