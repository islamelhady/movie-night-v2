package com.elhady.movies.ui.home

data class HomeUiState(
    val popularMovie: HomeItem = HomeItem.Slider(emptyList()),
)