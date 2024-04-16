package com.elhady.movies.presentation.viewmodel.myrated.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.myrated.MyRatedTvShowEntity
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState
import javax.inject.Inject

class MyRatedTvShowToMovieHorizontalUiMapper @Inject constructor()  :
    Mapper<MyRatedTvShowEntity, MovieHorizontalUIState> {
    override fun map(input: MyRatedTvShowEntity): MovieHorizontalUIState {
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