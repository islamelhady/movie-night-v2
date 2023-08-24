package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.data.remote.test.Movie
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class MovieImageAdapter(items: List<Movie>, listener: MovieInteractionListener) :
    BaseAdapter<Movie>(items, listener) {
    override val layoutID: Int = R.layout.item_movie
    override fun areItemContent(oldItem: Movie, newItem: Movie): Boolean {
        return true
    }
}

interface MovieInteractionListener : BaseInteractionListener {
    fun onClickMovie(name: String)
}


