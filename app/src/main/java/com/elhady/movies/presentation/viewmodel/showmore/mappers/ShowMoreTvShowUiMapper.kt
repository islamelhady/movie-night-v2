package com.elhady.movies.presentation.viewmodel.showmore.mappers

import com.elhady.movies.core.bases.ListType
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.presentation.viewmodel.showmore.ShowMoreUi
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
            type = ListType.tv
        )
    }

    private fun convertGenreListToString(input: List<GenreEntity>): String {
        return input.joinToString(" | ") { it.genreName }
    }

}