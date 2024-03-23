package com.elhady.movies.core.data.remote.response


import com.google.gson.annotations.SerializedName

data class GenresWrapperResponse<T>(
    @SerializedName("genres")
    val results: List<T>?
)