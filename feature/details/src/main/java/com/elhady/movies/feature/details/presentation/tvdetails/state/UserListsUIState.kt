package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.state.UserListUiState

sealed interface UserListsUIState {
    object Idle : UserListsUIState

    object Loading : UserListsUIState

    data class Success(
        val lists: List<UserListUiState>
    ) : UserListsUIState

    data class Error(
        val error: ErrorUiState
    ) : UserListsUIState
}

