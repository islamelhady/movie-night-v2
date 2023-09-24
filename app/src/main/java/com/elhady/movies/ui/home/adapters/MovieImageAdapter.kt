package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class MovieImageAdapter(items: List<MovieDto>, listener: MovieInteractionListener) :
    BaseAdapter<MovieDto>(items, listener) {
    override val layoutID: Int = R.layout.item_movie
    override fun areItemContent(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return true
    }
}

interface MovieInteractionListener : BaseInteractionListener {
    fun onClickMovie(name: String)
}


