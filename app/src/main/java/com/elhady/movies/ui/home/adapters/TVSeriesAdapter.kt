package com.elhady.movies.ui.home.adapters

import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.MediaUiState

class TVSeriesAdapter(items: List<MediaUiState>, val listener: TVSeriesInteractionListener, layout: Int): BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = layout
}

interface TVSeriesInteractionListener: BaseInteractionListener{
    fun onClickTVSeries(seriesID: Int)
    fun onClickAllTVSeries(type: SeeAllType)
}