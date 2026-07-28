package com.elhady.movies.feature.watchlist.presentation.mylist.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.account.ListCreatedEntity
import com.elhady.movies.feature.watchlist.presentation.mylist.ListMovieUiState
import javax.inject.Inject

class MyListUiMapper @Inject constructor() : Mapper<ListCreatedEntity, ListMovieUiState> {
    override fun map(input: ListCreatedEntity): ListMovieUiState {
        return ListMovieUiState(
            id = input.id ?: 0,
            itemCount = input.itemCount,
            listType = input.listType,
            name = input.name,
            posterPath = input.posterPath,
        )
    }
}
