package com.elhady.viewmodel.profile.watchHistory


data class MediaHistoryUiState(
    val id: Int,
    var image: String,
    var title: String,
    var mediaDuration: Int,
    var voteAverage: String,
    var releaseDate: String,
    var mediaType: String
)