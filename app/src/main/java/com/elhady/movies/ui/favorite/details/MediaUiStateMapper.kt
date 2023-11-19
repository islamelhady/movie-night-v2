package com.elhady.movies.ui.favorite.details

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.SaveListDetails
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