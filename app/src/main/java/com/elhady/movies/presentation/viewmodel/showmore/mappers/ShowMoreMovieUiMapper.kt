package com.elhady.movies.presentation.viewmodel.showmore.mappers

import com.elhady.movies.core.bases.ListType
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.showmore.ShowMoreUi
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