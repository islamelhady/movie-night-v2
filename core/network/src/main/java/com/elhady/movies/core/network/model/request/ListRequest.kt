package com.elhady.movies.core.network.model.request


import com.google.gson.annotations.SerializedName

data class ListRequest(
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("language")
    val language: String? = "en",
    @SerializedName("name")
    val name: String? = null
)
