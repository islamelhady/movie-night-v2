package com.elhady.movies.core.network.dto.account


import com.google.gson.annotations.SerializedName

data class UserListDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
)
