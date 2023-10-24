package com.elhady.movies.ui.actorDetails

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.home.adapters.MovieInteractionListener

class ActorMoviesAdapter(items: List<ActorMoviesUiState>, listener: MovieInteractionListener) :
    BaseAdapter<ActorMoviesUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie_actor
}