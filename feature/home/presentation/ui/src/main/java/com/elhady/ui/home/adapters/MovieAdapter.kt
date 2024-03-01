package com.elhady.ui.home.adapters

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.models.MediaUiState


class MovieAdapter(items: List<MediaUiState>, val listener: MovieInteractionListener) :
    BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie
}

interface MovieInteractionListener : BaseInteractionListener {
    fun onClickMovie(movieID: Int)
    fun onClickSeeAllMovies(mediaType: SeeAllType)
}