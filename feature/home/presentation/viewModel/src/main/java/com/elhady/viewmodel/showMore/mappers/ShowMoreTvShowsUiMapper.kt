package com.elhady.viewmodel.showMore.mappers

import com.elhady.entities.GenreEntity
import com.elhady.entities.TvShowEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.showMore.ShowMoreUi
import javax.inject.Inject

class ShowMoreTvShowsUiMapper @Inject constructor() :
    Mapper<TvShowEntity, ShowMoreUi> {
    override fun map(input: TvShowEntity): ShowMoreUi {
        return ShowMoreUi(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl,
            rate = input.rate,
            genreEntities = convertGenreListToString(input.tvShowGenreEntities),

        )
    }

    private fun convertGenreListToString(input: List<GenreEntity>): String {
        return input.joinToString(" | ") { it.genreName }
    }

}