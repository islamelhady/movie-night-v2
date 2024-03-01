package com.elhady.viewmodel.mappers

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.models.MediaUiState
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