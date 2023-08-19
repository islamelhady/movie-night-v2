package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.HorizontalBaseAdapter

class MovieImageAdapter(private val items: List<String>): BaseAdapter<String>(items) {
    override val layoutID = R.layout.item_movie_image

    override fun areItemContent(oldItem: String, newItem: String): Boolean {
        return true
    }

    class HorizontalImageAdapter(adapter: MovieImageAdapter): HorizontalBaseAdapter<MovieImageAdapter>(adapter){
        override val layoutID = R.layout.base_recycler_view
    }


}