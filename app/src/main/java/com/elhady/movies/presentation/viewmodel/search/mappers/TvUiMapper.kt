package com.elhady.movies.presentation.viewmodel.search.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.TvEntity
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState
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