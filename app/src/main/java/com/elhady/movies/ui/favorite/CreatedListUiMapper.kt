package com.elhady.movies.ui.favorite

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.CreatedList
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


