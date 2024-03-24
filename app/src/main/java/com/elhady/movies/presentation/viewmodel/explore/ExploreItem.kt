package com.elhady.movies.presentation.viewmodel.explore


sealed class ExploreItem(val type: LayoutItemType) {
    data class HorizontalItem(val horizontalItem: ExploreUiState.TrendingMoviesUiState): ExploreItem(LayoutItemType.HORIZONTAL)
    data class GridItem(val gridItem: ExploreUiState.TrendingMoviesUiState): ExploreItem(LayoutItemType.GRID)
}
enum class LayoutItemType{
    GRID,
    HORIZONTAL
}
