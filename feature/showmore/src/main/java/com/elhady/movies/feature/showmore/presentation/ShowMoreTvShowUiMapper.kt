package com.elhady.movies.feature.showmore.presentation

import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.GenreEntity
import com.elhady.movies.core.domain.model.tvshow.TVShowsEntity
import javax.inject.Inject

class ShowMoreTvShowUiMapper @Inject constructor() :
    Mapper<TVShowsEntity, ShowMoreUi> {
    override fun map(input: TVShowsEntity): ShowMoreUi {
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

    private fun convertGenreListToString(input: List<GenreEntity>): String {
        return input.joinToString(" | ") { it.genreName }
    }

}
