package com.elhady.movies.ui.category

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Genre
import javax.inject.Inject

class GenreUiMapper @Inject constructor() : Mapper<Genre, CategoryGenreUiState> {
    override fun map(input: Genre): CategoryGenreUiState {
        return CategoryGenreUiState(
            id = input.id,
            name = input.name
        )
    }
}