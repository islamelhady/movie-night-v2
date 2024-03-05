package com.elhady.viewmodel.home.mappers

import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.NowPlayingMoviesUiState
import javax.inject.Inject

class NowPlayingMovieUiMapper @Inject constructor() : Mapper<MovieEntity, NowPlayingMoviesUiState> {
    override fun map(input: MovieEntity): NowPlayingMoviesUiState {
        return NowPlayingMoviesUiState(
            id = input.movieId,
            imageUrl = input.movieImage,
            rate = input.movieRate,
            title = input.movieName
        )
    }
}