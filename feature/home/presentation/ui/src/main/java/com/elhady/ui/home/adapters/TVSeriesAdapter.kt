package com.elhady.ui.home.adapters

import com.elhady.base.BaseInteractionListener
import com.elhady.viewmodel.models.MediaUiState


class TVSeriesAdapter(items: List<MediaUiState>, val listener: TVSeriesInteractionListener, layout: Int): BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = layout
}

interface TVSeriesInteractionListener: BaseInteractionListener {
    fun onClickTVSeries(seriesID: Int)
    fun onClickAllTVSeries(type: SeeAllType)
}