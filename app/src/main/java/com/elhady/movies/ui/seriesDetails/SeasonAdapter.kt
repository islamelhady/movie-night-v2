package com.elhady.movies.ui.seriesDetails

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.seriesDetails.seriesUiState.SeasonUiState

class SeasonAdapter(items: List<SeasonUiState>, listener: SeasonInteractionListener): BaseAdapter<SeasonUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_season
}

interface SeasonInteractionListener: BaseInteractionListener{
    fun onClickSeason(seasonNumber: Int)
}