package com.elhady.movies.feature.watchlist.presentation.lists.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.account.ListCreated
import com.elhady.movies.feature.watchlist.presentation.lists.ListMovieUiState
import javax.inject.Inject

class ListsUiMapper @Inject constructor() : Mapper<ListCreated, ListMovieUiState> {
    override fun map(input: ListCreated): ListMovieUiState {
        return ListMovieUiState(
            id = input.id ?: 0,
            itemCount = input.itemCount,
            listType = input.listType,
            name = input.name,
            posterPath = input.posterPath ?: emptyList(),
        )
    }
}
