package com.elhady.movies.ui.search

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class HistorySearchAdapter(items: List<SearchHistoryUiState>, listener: HistoryInteractionListener): BaseAdapter<SearchHistoryUiState>(items, listener){
    override val layoutID: Int = R.layout.item_search_history
}

interface HistoryInteractionListener: BaseInteractionListener{
    fun onClickHistorySearch(search: String)
    fun onClickClearSearchHistoryItem(search: String)
}