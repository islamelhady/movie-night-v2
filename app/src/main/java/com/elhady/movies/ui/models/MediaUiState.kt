package com.elhady.movies.ui.models

import com.elhady.movies.ui.search.MediaTypes

data class MediaUiState(
    val id: Int = 0,
    val imageUrl: String = "",
    val name: String = "",
    val rate: Float = 0f,
    val date: String = "",
    val mediaTypes: String = ""
)
