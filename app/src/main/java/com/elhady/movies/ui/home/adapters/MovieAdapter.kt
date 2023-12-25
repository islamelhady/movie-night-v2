package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.MediaUiState

class MovieAdapter(items: List<MediaUiState>, val listener: MovieInteractionListener) :
    BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie
}

interface MovieInteractionListener : BaseInteractionListener {
    fun onClickMovie(movieID: Int)
    fun onClickSeeAllMovies(mediaType: SeeAllType)
}