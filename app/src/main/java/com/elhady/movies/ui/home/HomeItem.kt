package com.elhady.movies.ui.home

import com.elhady.movies.ui.home.homeUiState.PopularUiState

sealed interface HomeItem{
    data class Slider(val items: List<PopularUiState>): HomeItem
}