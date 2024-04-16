package com.elhady.movies.presentation.viewmodel.explore

import com.elhady.movies.presentation.viewmodel.common.listener.MovieListener

interface ExploreListener: MovieListener {
    fun onClickSearch()
}