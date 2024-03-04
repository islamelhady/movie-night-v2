package com.elhady.viewmodel.home.mappers

import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.MysteryMoviesUiState
import javax.inject.Inject

class MysteryMovieUiMapper @Inject constructor() : Mapper<MovieEntity, MysteryMoviesUiState> {
    override fun map(input: MovieEntity): MysteryMoviesUiState {
        return MysteryMoviesUiState(
            id = input.movieId,
            rate = input.movieRate,
            imageUrl = input.movieImage,
            title = input.movieName
        )
    }
}