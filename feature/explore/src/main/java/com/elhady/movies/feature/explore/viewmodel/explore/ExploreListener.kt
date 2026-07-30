package com.elhady.movies.feature.explore.viewmodel.explore

import com.elhady.movies.core.ui.interaction.MovieListener

interface ExploreListener: MovieListener {
    fun onClickSearch()
}
