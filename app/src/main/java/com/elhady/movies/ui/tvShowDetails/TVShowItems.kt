package com.elhady.movies.ui.tvShowDetails

sealed class TVShowItems(val priority: Int){
    data class Header(val data: TVShowDetailsResultUiState): TVShowItems(0)
}
