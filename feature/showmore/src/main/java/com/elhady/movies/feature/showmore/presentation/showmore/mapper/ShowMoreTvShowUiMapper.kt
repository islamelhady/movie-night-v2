package com.elhady.movies.feature.showmore.presentation.showmore.mapper

import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.feature.showmore.presentation.showmore.ShowMoreUi
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
