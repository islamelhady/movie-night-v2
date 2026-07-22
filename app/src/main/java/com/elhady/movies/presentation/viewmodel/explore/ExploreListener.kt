package com.elhady.movies.presentation.viewmodel.explore

import com.elhady.movies.core.common.presentation.MovieListener

interface ExploreListener: MovieListener {
    fun onClickSearch()
}
