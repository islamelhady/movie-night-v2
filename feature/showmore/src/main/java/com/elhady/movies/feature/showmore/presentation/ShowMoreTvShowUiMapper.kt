package com.elhady.movies.feature.showmore.presentation

import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.tvshow.TvShows
import javax.inject.Inject

class ShowMoreTvShowUiMapper @Inject constructor() :
    Mapper<TvShows, ShowMoreUi> {
    override fun map(input: TvShows): ShowMoreUi {
        return ShowMoreUi(
            id = input.id,
            name = input.title,
            imageUrl = input.imageUrl,
            rate = input.rate,
            year = input.year,
            genreEntities = convertGenreListToString(input.genreEntities),
            type = ListType.TV
        )
    }

    private fun convertGenreListToString(input: List<Genre>): String {
        return input.joinToString(" | ") { it.genreName }
    }

}
