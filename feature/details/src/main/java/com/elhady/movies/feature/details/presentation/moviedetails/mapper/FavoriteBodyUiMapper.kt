package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.FavoriteBodyRequest
import com.elhady.movies.feature.details.presentation.moviedetails.FavoriteBodyUiState
import javax.inject.Inject

class FavoriteBodyUiMapper @Inject constructor() :
    Mapper<FavoriteBodyRequest, FavoriteBodyUiState> {
    override fun map(input: FavoriteBodyRequest): FavoriteBodyUiState {
        return FavoriteBodyUiState(
            isFavorite = input.isFavorite,
            mediaId = input.mediaId,
            mediaType = input.mediaType,
        )
    }

}
