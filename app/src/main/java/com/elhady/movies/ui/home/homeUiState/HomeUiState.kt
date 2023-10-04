package com.elhady.movies.ui.home.homeUiState

import com.elhady.movies.ui.home.HomeItem

data class HomeUiState(
    val popularMovie: HomeItem = HomeItem.Slider(emptyList()),
)