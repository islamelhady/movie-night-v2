package com.elhady.movies.core.network.model.request


import com.google.gson.annotations.SerializedName

data class CreateUserListRequest(
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description:String? = "",
    @SerializedName("language")
    val lang:String = "en"
)
