package com.elhady.viewmodel.favorite.details

import com.elhady.mapper.Mapper
import javax.inject.Inject

class MediaUiStateMapper @Inject constructor() : Mapper<SaveListDetails, FavMediaUiState> {
    override fun map(input: SaveListDetails): FavMediaUiState {
        return FavMediaUiState(
            image = input.posterPath,
            mediaID = input.id,
            title = input.title,
            voteAverage = input.voteAverage,
            releaseDate = input.releaseDate,
            mediaType = input.mediaType
        )
    }
}