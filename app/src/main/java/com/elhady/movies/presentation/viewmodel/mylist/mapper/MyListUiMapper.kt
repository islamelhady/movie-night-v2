package com.elhady.movies.presentation.viewmodel.mylist.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.mylist.ListCreatedEntity
import com.elhady.movies.presentation.viewmodel.mylist.ListMovieUiState
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