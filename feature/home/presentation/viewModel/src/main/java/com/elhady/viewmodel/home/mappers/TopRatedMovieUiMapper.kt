package com.elhady.viewmodel.home.mappers

import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.TopRatedMoviesUiState
import javax.inject.Inject

class TopRatedMovieUiMapper @Inject constructor() : Mapper<MovieEntity, TopRatedMoviesUiState> {
    override fun map(input: MovieEntity): TopRatedMoviesUiState {
        return TopRatedMoviesUiState(
            id = input.movieId,
            rate = input.movieRate,
            imageUrl = input.movieImage,
            title = input.movieName
        )
    }
}