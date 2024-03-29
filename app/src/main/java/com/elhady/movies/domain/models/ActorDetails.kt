package com.elhady.movies.domain.models

data class ActorDetails (
    val id: Int,
    val name: String,
    val image: String,
    val gender: String,
    val birthday: String,
    val placeOfBirth: String,
    val biography: String,
    val knownForDepartment: String
)