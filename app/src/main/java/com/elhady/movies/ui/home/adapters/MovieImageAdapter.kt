package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.data.Movie
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.HorizontalBaseAdapter
import com.elhady.movies.ui.home.HomeViewModel

class MovieImageAdapter(private val items: List<Movie>) : BaseAdapter<Movie>(items) {
    override val layoutID = R.layout.item_movie_image
    override fun areItemContent(oldItem: Movie, newItem: Movie): Boolean {
        return true
    }
}

class HorizontalMovieImageAdapter(adapter: MovieImageAdapter, viewModel: HomeViewModel) :
    HorizontalBaseAdapter<Movie>(adapter, viewModel)


