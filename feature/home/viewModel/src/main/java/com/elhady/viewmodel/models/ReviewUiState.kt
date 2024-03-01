package com.elhady.viewmodel.models

data class ReviewUiState(
    val userName: String = "",
    val userImage: String = "",
    val name: String = "",
    val content: String = "",
    val rating: Float = 0f,
    val createDate: String =""
)