package com.elhady.movies.ui.mappers

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import com.elhady.movies.ui.models.MediaUiState
import javax.inject.Inject

class MediaUiMapper @Inject constructor() : Mapper<Media, MediaUiState> {
    override fun map(input: Media): MediaUiState {
        return MediaUiState(
            id = input.mediaID,
            imageUrl = input.mediaImage,
            name = input.mediaName,
            rate = input.mediaRate,
            date = input.mediaDate,
            mediaTypes = input.mediaType
        )
    }
}