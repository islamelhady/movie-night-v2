package com.elhady.movies.presentation.viewmodel.mylist

data class MyListUiState(
    val movieList: List<ListMovieUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isShowDelete: Boolean = false,
    val newListName: String = "",
    val error: List<String>? = null,

    ){
    val isFailure: Boolean = error?.isNotEmpty() == true
}