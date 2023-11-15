package com.elhady.movies.ui.profile.watchHistory

import com.elhady.movies.domain.enums.MediaType

data class MediaHistoryUiState(
    val id: Int,
    var image: String,
    var title: String,
    var mediaDuration: Int,
    var voteAverage: String,
    var releaseDate: String,
    var mediaType: String
)