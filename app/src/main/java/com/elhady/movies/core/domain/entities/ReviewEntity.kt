package com.elhady.movies.core.domain.entities

data class ReviewEntity(
    val name:String,
    val avatar_path:String,
    val content:String,
    val created_at:String,
)

data class ReviewEntity(
    val name: String = "",
    val avatarPath: String = "",
    val content: String = "",
    val createdAt: String = "",
)