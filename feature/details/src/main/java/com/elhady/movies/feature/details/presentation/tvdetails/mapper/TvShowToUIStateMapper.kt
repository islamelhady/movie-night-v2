package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.core.ui.state.MediaVerticalUiState
import javax.inject.Inject

class TvShowToUIStateMapper @Inject constructor() : Mapper<TvShow, MediaVerticalUiState> {
    override fun map(input: TvShow): MediaVerticalUiState {
        return MediaVerticalUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }

}
