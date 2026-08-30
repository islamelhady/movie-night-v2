package com.elhady.movies.feature.search.presentation.search.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.tvshow.Tv
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import javax.inject.Inject

class TvUiMapper @Inject constructor()  : Mapper<Tv, MovieHorizontalUiState> {
    override fun map(input: Tv): MovieHorizontalUiState {
        return MovieHorizontalUiState(
            id = input.id,
            rate = input.rate,
            title = input.title,
            imageUrl = input.imageUrl,
            year = input.extractYearFromDate(),
            genres = input.convertGenreListToString()
        )
    }
}
