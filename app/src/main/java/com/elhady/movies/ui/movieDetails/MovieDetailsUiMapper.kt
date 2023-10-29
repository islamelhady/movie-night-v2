package com.elhady.movies.ui.movieDetails

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.MovieDetails
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor() : Mapper<MovieDetails, MovieDetailsUiState> {
    override fun map(input: MovieDetails): MovieDetailsUiState {
        return MovieDetailsUiState(
            id = input.id,
            name = input.name,
            image = input.image
        )
    }
}