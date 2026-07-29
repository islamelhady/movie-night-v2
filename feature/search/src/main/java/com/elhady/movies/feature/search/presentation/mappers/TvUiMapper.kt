package com.elhady.movies.feature.search.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.Tv
import com.elhady.movies.core.ui.model.MovieHorizontalUIState
import javax.inject.Inject

class TvUiMapper @Inject constructor()  : Mapper<Tv, MovieHorizontalUIState> {
    override fun map(input: Tv): MovieHorizontalUIState {
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
