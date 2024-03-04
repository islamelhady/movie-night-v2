package com.elhady.viewmodel.home.mappers

import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.AdventureMoviesUiState
import javax.inject.Inject

class AdventureMovieUiMapper @Inject constructor() : Mapper<MovieEntity, AdventureMoviesUiState> {
    override fun map(input: MovieEntity): AdventureMoviesUiState {
        return AdventureMoviesUiState(
            id = input.movieId,
            rate = input.movieRate,
            imageUrl = input.movieImage,
            title = input.movieName
        )
    }
}