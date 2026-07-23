package com.elhady.movies.feature.search.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.TvEntity
import com.elhady.movies.core.common.presentation.model.MovieHorizontalUIState
import javax.inject.Inject

class TvUiMapper @Inject constructor()  : Mapper<TvEntity, MovieHorizontalUIState> {
    override fun map(input: TvEntity): MovieHorizontalUIState {
        return MovieHorizontalUIState(
            id = input.id,
            rate = input.rate,
            title = input.title,
            imageUrl = input.imageUrl,
            year = input.extractYearFromDate(),
            genres = input.convertGenreListToString()
        )
    }
}
