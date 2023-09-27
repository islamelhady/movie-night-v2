package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.home.HomeUiState
import com.elhady.movies.ui.home.PopularUiState

class MovieImageAdapter(items: List<PopularUiState>, listener: MovieInteractionListener) :
    BaseAdapter<PopularUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_popular
    override fun areItemContent(oldItem: PopularUiState, newItem: PopularUiState): Boolean {
        return true
    }
}

interface MovieInteractionListener : BaseInteractionListener {
    fun onClickMovie(name: String)
}


