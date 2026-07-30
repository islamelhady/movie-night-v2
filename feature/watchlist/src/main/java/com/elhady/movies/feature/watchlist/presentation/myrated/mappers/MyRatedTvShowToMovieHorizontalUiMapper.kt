package com.elhady.movies.feature.watchlist.presentation.myrated.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.ui.state.MovieHorizontalUIState
import javax.inject.Inject

class MyRatedTvShowToMovieHorizontalUiMapper @Inject constructor()  :
    Mapper<MyRatedTvShow, MovieHorizontalUIState> {
    override fun map(input: MyRatedTvShow): MovieHorizontalUIState {
        return MovieHorizontalUIState(
            id = input.id,
            rate = input.rate,
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
