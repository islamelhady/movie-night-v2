package com.elhady.viewmodel.category

import com.elhady.mapper.Mapper
import javax.inject.Inject

class GenreUiMapper @Inject constructor() : Mapper<Genre, CategoryGenreUiState> {
    override fun map(input: Genre): CategoryGenreUiState {
        return CategoryGenreUiState(
            id = input.id,
            name = input.name
        )
    }
}