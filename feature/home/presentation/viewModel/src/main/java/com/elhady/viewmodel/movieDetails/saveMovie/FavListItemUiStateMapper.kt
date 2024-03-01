package com.elhady.viewmodel.movieDetails.saveMovie

import com.elhady.mapper.Mapper
import javax.inject.Inject

class FavListItemUiStateMapper @Inject constructor() : Mapper<CreatedList, FavListItemUiState> {
    override fun map(input: CreatedList): FavListItemUiState {
        return FavListItemUiState(
            listId = input.id,
            name = input.name
        )
    }
}