package com.elhady.movies.feature.search.presentation.mappers

import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.feature.search.presentation.SearchUiState
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
