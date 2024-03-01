package com.elhady.ui.actorDetails

import com.elhady.base.BaseAdapter
import com.elhady.ui.home.adapters.MovieInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.actorDetails.ActorMoviesUiState

class ActorMoviesAdapter(items: List<ActorMoviesUiState>, listener: MovieInteractionListener) :
    BaseAdapter<ActorMoviesUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie_actor
}