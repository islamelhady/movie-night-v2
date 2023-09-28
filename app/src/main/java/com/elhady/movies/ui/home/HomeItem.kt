package com.elhady.movies.ui.home

sealed interface HomeItem{
    data class Slider(val items: List<PopularUiState>): HomeItem
}