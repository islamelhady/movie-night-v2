package com.elhady.ui.search

import com.elhady.base.BaseInteractionListener
import com.elhady.movies.ui.search.SearchHistoryUiState
import com.elhady.ui.R

class HistorySearchAdapter(items: List<SearchHistoryUiState>, listener: HistoryInteractionListener): BaseAdapter<SearchHistoryUiState>(items, listener){
    override val layoutID: Int = R.layout.item_search_history
}

interface HistoryInteractionListener: BaseInteractionListener {
    fun onClickHistorySearch(search: String)
    fun onClickClearSearchHistoryItem(search: String)
}