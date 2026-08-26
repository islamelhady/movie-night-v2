package com.elhady.movies.feature.showmore.presentation.showmore.mapper

import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.showmore.presentation.showmore.ShowMoreUi
import javax.inject.Inject

class ShowMoreMovieUiMapper @Inject constructor() :
    Mapper<Movie, ShowMoreUi> {
    override fun map(input: Movie): ShowMoreUi {
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

    private fun convertGenreListToString(input: List<Genre>): String {
        return input.joinToString(" | ") { it.genreName }
    }

}
