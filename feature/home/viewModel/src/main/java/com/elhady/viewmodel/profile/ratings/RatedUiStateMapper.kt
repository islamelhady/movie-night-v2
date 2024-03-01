package com.elhady.viewmodel.profile.ratings

import com.elhady.mapper.Mapper
import javax.inject.Inject

class RatedUiStateMapper @Inject constructor() : Mapper<Rated, RatedUiState> {
    override fun map(input: Rated): RatedUiState {
        return RatedUiState(
            id = input.id,
            title = input.title,
            posterPath = input.posterPath,
            rating = input.rating,
            mediaType = input.mediaType,
            releaseDate = input.releaseDate
        )
    }
}