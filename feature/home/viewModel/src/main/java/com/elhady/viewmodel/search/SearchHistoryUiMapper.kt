package com.elhady.viewmodel.search

import com.elhady.mapper.Mapper
import com.elhady.movies.ui.search.SearchHistoryUiState
import javax.inject.Inject


class SearchHistoryUiMapper @Inject constructor() : Mapper<SearchHistory, SearchHistoryUiState> {
    override fun map(input: SearchHistory): SearchHistoryUiState {
        return SearchHistoryUiState(
            name = input.name
        )
    }
}