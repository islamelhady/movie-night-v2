package com.elhady.movies.feature.search.presentation.search.mapper

import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.feature.search.presentation.search.SearchUiState
import javax.inject.Inject

class GenreUiMapper @Inject constructor() {
    fun map(input: Genre, isSelected: Boolean): SearchUiState.GenresUiState {
        return SearchUiState.GenresUiState(
            genreId = input.genreID,
            genresName = input.genreName,
            isSelected = isSelected
        )
    }
}
