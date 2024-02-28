package com.elhady.entities


data class CreatedListEntity(
    val id: Int,
    val itemCount: Int,
    val name: String,
    val listType: String,
    val posterPath: List<String>
)
