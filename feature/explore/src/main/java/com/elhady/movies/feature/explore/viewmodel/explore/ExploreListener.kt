package com.elhady.movies.feature.explore.viewmodel.explore

import com.elhady.movies.core.common.presentation.MovieListener

interface ExploreListener: MovieListener {
    fun onClickSearch()
}
