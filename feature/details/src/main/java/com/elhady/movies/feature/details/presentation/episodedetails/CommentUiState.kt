package com.elhady.movies.feature.details.presentation.episodedetails

data class CommentUiState(
    val name: String,
    val content: String,
) {
    fun getFirstTwoCharsFromName(): String {
        return name.split(" ").map {
            it.first()
        }.joinToString("")
    }
}
