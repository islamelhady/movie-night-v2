package com.elhady.movies.presentation.viewmodel.youtubetrailer

data class YoutubePlayerUIState(
    val videoKey: String = "",
    val isLoading: Boolean = false,
    val errors: List<String>? = emptyList(),
) {
    val isError: Boolean
        get() = errors?.isNotEmpty() ?: false
}