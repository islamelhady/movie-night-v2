package com.elhady.viewmodel.home.mappers

import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.UpcomingMoviesUiState
import javax.inject.Inject

class UpcomingMovieUiMapper @Inject constructor() : Mapper<MovieEntity, UpcomingMoviesUiState> {
    override fun map(input: MovieEntity): UpcomingMoviesUiState {
        return UpcomingMoviesUiState(
            id = input.movieId,
            imageUrl = input.movieImage,
            title = input.movieName
        )
    }
}