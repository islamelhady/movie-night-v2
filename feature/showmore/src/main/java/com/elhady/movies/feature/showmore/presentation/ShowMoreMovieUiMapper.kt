package com.elhady.movies.feature.showmore.presentation

import com.elhady.movies.core.common.bases.ListType
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.model.MovieEntity
import javax.inject.Inject

class ShowMoreMovieUiMapper @Inject constructor() :
    Mapper<MovieEntity, ShowMoreUi> {
    override fun map(input: MovieEntity): ShowMoreUi {
        return ShowMoreUi(
            id = input.id,
            name = input.title,
            imageUrl = input.imageUrl,
            year = input.year,
            genreEntities = convertGenreListToString(input.genreEntities),
            rate = input.rate,
            ListType.MOVIE
        )
    }

    private fun convertGenreListToString(input: List<GenreEntity>): String {
        return input.joinToString(" | ") { it.genreName }
    }

}
