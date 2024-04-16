package com.elhady.movies.presentation.viewmodel.search.mappers

import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.presentation.viewmodel.search.SearchUiState
import javax.inject.Inject

class GenreUiStateMapper @Inject constructor() {
    fun map(input: GenreEntity, isSelected: Boolean): SearchUiState.GenresUiState {
        return SearchUiState.GenresUiState(
            genreId = input.genreID,
            genresName = input.genreName,
            isSelected = isSelected
        )
    }
}