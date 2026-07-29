package com.elhady.movies.feature.watchlist.presentation.myrated.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import com.elhady.movies.core.ui.model.MovieHorizontalUIState
import javax.inject.Inject

class MyRatedMovieToMovieHorizontalUiMapper @Inject constructor()  :
    Mapper<MyRatedMovie, MovieHorizontalUIState> {
    override fun map(input: MyRatedMovie): MovieHorizontalUIState {
        return MovieHorizontalUIState(
            id = input.id,
            rate = input.myRate,
            title = input.title,
            imageUrl = input.imageUrl,
            year = extractYearFromDate(input.year),
            genres = convertGenreListToString(input.genreEntities.map { it.genreName }),
        )
    }

    private fun convertGenreListToString(list: List<String>): String {
        return list.joinToString(" | ")
    }

    private fun extractYearFromDate(year: String): String {
        val parts = year.split("-")
        return parts[0]
    }
}
