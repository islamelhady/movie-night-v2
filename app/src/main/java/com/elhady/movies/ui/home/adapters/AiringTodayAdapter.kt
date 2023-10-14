package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.MediaUiState

class AiringTodayAdapter(items: List<MediaUiState>, listener: AiringTodayInteractionListener): BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_airing_today
}

interface AiringTodayInteractionListener: BaseInteractionListener{
    fun onClick(mediaID: Int)
}