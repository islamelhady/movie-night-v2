package com.elhady.movies.feature.details.presentation.youtubeplayer

data class YoutubePlayerUIState(
    val videoKey: String = "",
    val isLoading: Boolean = false,
    val errors: List<String>? = emptyList(),
) {
    val isError: Boolean
        get() = errors?.isNotEmpty() ?: false
}
