package com.elhady.viewmodel.mappers

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.profile.watchHistory.MediaHistoryUiState
import javax.inject.Inject

class WatchHistoryUiMapper @Inject constructor() : Mapper<WatchHistoryEntity, MediaHistoryUiState> {
    override fun map(input: WatchHistoryEntity): MediaHistoryUiState {
        return MediaHistoryUiState(
            id = input.id,
            image = input.image,
            title = input.title,
            voteAverage = input.voteAverage,
            releaseDate = input.releaseDate,
            mediaDuration = input.movieDuration,
            mediaType = input.mediaType,
        )
    }
}