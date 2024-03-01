package com.elhady.viewmodel.explore

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import com.elhady.viewmodel.explore.TrendingMediaUiState
import javax.inject.Inject

class TrendingUiStateMapper @Inject constructor() : Mapper<Media, TrendingMediaUiState> {
    override fun map(input: Media): TrendingMediaUiState {
        return TrendingMediaUiState(
            id = input.mediaID,
            name = input.mediaName,
            image = input.mediaImage,
            data = input.mediaDate,
            type = input.mediaType,
            rate = input.mediaRate
        )
    }
}