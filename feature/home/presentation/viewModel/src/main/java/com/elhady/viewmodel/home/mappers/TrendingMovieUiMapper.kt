package com.elhady.viewmodel.home.mappers

import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.TrendingMoviesUiState
import javax.inject.Inject

class TrendingMovieUiMapper @Inject constructor() : Mapper<MovieEntity, TrendingMoviesUiState> {
    override fun map(input: MovieEntity): TrendingMoviesUiState {
        return TrendingMoviesUiState(
            id = input.movieId,
            rate = input.movieRate,
            imageUrl = input.movieImage,
        )
    }
}