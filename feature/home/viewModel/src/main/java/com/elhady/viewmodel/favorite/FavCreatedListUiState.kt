package com.elhady.viewmodel.favorite


data class FavCreatedListUiState(
    val createdList: List<CreatedListUiState> = emptyList(),
    val dialogUiState: CreateListDialogUiState = CreateListDialogUiState(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isLoggedIn: Boolean = false,
    val onErrors: List<String> = emptyList()
)

data class CreatedListUiState(
    val id: Int = 0,
    val name: String = "",
    val mediaCounts: Int = 0,
    val listType: String = "",
    val posterPath: List<String> = emptyList()
)

data class CreateListDialogUiState(
    val listName: String = "",
    val onErrors: List<String> = emptyList()
)