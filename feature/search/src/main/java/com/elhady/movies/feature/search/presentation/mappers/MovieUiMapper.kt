package com.elhady.movies.feature.search.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import javax.inject.Inject

class MovieUiMapper @Inject constructor()  : Mapper<Movie, MovieHorizontalUiState> {
    override fun map(input: Movie): MovieHorizontalUiState {
        return MovieHorizontalUiState(
            id = input.id,
            rate = input.rate,
            title = input.title,
            imageUrl = input.imageUrl,
            year = extractYearFromDate(input.year),
            genres = convertGenreListToString(input.genreEntities),
        )
    }

    private fun convertGenreListToString(list: List<Genre>): String {
        return list.joinToString(" | ") { it.genreName }
    }

    private fun extractYearFromDate(year: String): String {
        // Split the input string 'year' into parts using "-" as the delimiter.
        val parts = year.split("-")
        // Return the first part of the split string, which should be the year.
        return parts[0]
    }
}
