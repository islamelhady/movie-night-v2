package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.feature.details.presentation.tvdetails.state.TrailerUIState
import javax.inject.Inject

class TvShowYoutubeVideoDetailsUiMapper @Inject constructor() : Mapper<YoutubeVideoDetails, TrailerUIState.Trailer> {
    override fun map(input: YoutubeVideoDetails): TrailerUIState.Trailer {
        return TrailerUIState.Trailer(youtubeKey = input.key)
    }
}
