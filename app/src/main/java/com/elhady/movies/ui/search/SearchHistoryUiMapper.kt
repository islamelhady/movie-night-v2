package com.elhady.movies.ui.search

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.SearchHistory
import javax.inject.Inject


class SearchHistoryUiMapper @Inject constructor() : Mapper<SearchHistory, SearchHistoryUiState> {
    override fun map(input: SearchHistory): SearchHistoryUiState {
        return SearchHistoryUiState(
            name = input.name
        )
    }
}