package com.elhady.movies.ui.tvShowDetails

sealed class TVShowItems(val priority: Int){
    data class Details(val data: TVShowDetailsResultUiState): TVShowItems(0)
}
