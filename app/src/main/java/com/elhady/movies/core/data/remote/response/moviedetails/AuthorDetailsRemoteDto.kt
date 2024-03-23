package com.elhady.movies.core.data.remote.response.moviedetails


import com.google.gson.annotations.SerializedName

data class AuthorDetailsRemoteDto(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("username")
    val username: String?
)