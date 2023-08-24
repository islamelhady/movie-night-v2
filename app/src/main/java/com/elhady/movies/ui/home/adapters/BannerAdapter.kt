package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.data.remote.test.Movie
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class BannerAdapter(items: List<Movie>, listener: BannerInteractionListener) :
    BaseAdapter<Movie>(items, listener) {
    override val layoutID: Int = R.layout.item_banner
    override fun areItemContent(oldItem: Movie, newItem: Movie): Boolean {
        return true
    }
}

interface BannerInteractionListener : BaseInteractionListener {
    fun onClickBanner(name: String)
}