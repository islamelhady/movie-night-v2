package com.elhady.viewmodel.favorite

import com.elhady.mapper.Mapper
import javax.inject.Inject

class CreatedListUiMapper @Inject constructor() : Mapper<CreatedList, CreatedListUiState> {

    override fun map(input: CreatedList): CreatedListUiState {
        return CreatedListUiState(
            id = input.id,
            name = input.name,
            mediaCounts = input.itemCount,
            posterPath = input.posterPath
        )
    }
}


