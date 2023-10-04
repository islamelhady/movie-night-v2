package com.elhady.movies.ui.adapters

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.MediaUiState

class TrendingAdapter(items: List<MediaUiState>, listener: TrendingInteractionListener): BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie
}

interface TrendingInteractionListener: BaseInteractionListener{
    fun onClick(movieID: Int){
    }
}