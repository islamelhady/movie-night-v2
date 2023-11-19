package com.elhady.movies.ui.movieDetails.saveMovie

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.CreatedList
import javax.inject.Inject

class FavListItemUiStateMapper @Inject constructor() : Mapper<CreatedList, FavListItemUiState> {
    override fun map(input: CreatedList): FavListItemUiState {
        return FavListItemUiState(
            listId = input.id,
            name = input.name
        )
    }
}