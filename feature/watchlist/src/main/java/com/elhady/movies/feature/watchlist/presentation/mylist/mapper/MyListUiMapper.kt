package com.elhady.movies.feature.watchlist.presentation.mylist.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.ListCreated
import com.elhady.movies.feature.watchlist.presentation.mylist.ListMovieUiState
import javax.inject.Inject

class MyListUiMapper @Inject constructor() : Mapper<ListCreated, ListMovieUiState> {
    override fun map(input: ListCreated): ListMovieUiState {
        return ListMovieUiState(
            id = input.id ?: 0,
            itemCount = input.itemCount,
            listType = input.listType,
            name = input.name,
            posterPath = input.posterPath,
        )
    }
}
