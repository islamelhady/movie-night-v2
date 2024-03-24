package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.mylist.FavoriteBodyRequestEntity
import com.elhady.movies.presentation.viewmodel.moviedetails.FavoriteBodyUiState
import javax.inject.Inject

class FavoriteBodyUiMapper @Inject constructor() :
    Mapper<FavoriteBodyRequestEntity, FavoriteBodyUiState> {
    override fun map(input: FavoriteBodyRequestEntity): FavoriteBodyUiState {
        return FavoriteBodyUiState(
            isFavorite = input.isFavorite,
            mediaId = input.mediaId,
            mediaType = input.mediaType,
        )
    }

}