package com.elhady.movies.feature.watchlist.presentation.ratedmedia.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import javax.inject.Inject

class RatedMediaMovieToMovieHorizontalUiMapper @Inject constructor()  :
    Mapper<MyRatedMovie, MovieHorizontalUiState> {
    override fun map(input: MyRatedMovie): MovieHorizontalUiState {
        return MovieHorizontalUiState(
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
