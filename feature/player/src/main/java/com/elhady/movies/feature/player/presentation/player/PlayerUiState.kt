package com.elhady.movies.feature.player.presentation.player

data class PlayerUiState(
    val videoKey: String = "",
    val isLoading: Boolean = false,
    val errors: List<String>? = emptyList(),
) {
    val isError: Boolean
        get() = errors?.isNotEmpty() ?: false
}
