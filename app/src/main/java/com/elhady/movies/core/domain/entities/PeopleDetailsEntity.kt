package com.elhady.movies.core.domain.entities

data class PeopleDetailsEntity(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val placeOfBirth: String,
    val gender: String,
    val biography: String,
    val acting: String,
    val num_movies: String,
    )