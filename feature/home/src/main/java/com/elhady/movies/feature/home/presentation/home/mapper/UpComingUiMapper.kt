package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.home.UpcomingMovieUiState
import javax.inject.Inject

class UpComingUiMapper @Inject constructor() : Mapper<Movie, UpcomingMovieUiState> {
    override fun map(input: Movie): UpcomingMovieUiState {
        return UpcomingMovieUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            title = input.title,
            genres = input.genreEntities.map { it.genreName },
            rate = input.rate
        )
    }
}
