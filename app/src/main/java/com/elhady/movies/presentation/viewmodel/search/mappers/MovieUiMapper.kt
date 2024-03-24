package com.elhady.movies.presentation.viewmodel.search.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState
import javax.inject.Inject

class MovieUiMapper @Inject constructor()  : Mapper<MovieEntity, MovieHorizontalUIState> {
    override fun map(input: MovieEntity): MovieHorizontalUIState {
        return MovieHorizontalUIState(
            id = input.id,
            rate = input.rate,
            title = input.title,
            imageUrl = input.imageUrl,
            year = extractYearFromDate(input.year),
            genres = convertGenreListToString(input.genreEntities),
        )
    }

    private fun convertGenreListToString(list: List<GenreEntity>): String {
        return list.joinToString(" | ") { it.genreName }
    }

    private fun extractYearFromDate(year: String): String {
        // Split the input string 'year' into parts using "-" as the delimiter.
        val parts = year.split("-")
        // Return the first part of the split string, which should be the year.
        return parts[0]
    }
}