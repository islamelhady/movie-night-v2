package com.elhady.movies.ui.mappers

import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.ui.profile.watchHistory.MediaHistoryUiState
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