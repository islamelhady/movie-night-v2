package com.elhady.movies.feature.watchlist.presentation.listcontents.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.watchlist.presentation.listcontents.MovieUiState
import javax.inject.Inject

class ListContentsUiMapper @Inject constructor() : Mapper<Movie, MovieUiState> {
    override fun map(input: Movie): MovieUiState {
        return MovieUiState(
            id= input.id,
            title = input.title,
            imageUrl =input.imageUrl,
            genres = input.convertGenreListToString(),
            year = input.extractYearFromDate(),
            rating = input.rate,
            mediaType =input.mediaType,
        )
    }

    private fun Movie.convertGenreListToString(): String {
        return genreEntities.joinToString(" | ") { it.genreName }
    }

    private fun Movie.extractYearFromDate(): String {
        val parts = year.split("-")
        return parts[0]
    }
}
