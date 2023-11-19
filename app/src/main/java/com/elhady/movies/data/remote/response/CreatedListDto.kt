package com.elhady.movies.data.remote.response


import com.google.gson.annotations.SerializedName

data class CreatedListDto(
    @SerializedName("description")
    val description: String?,
    @SerializedName("favorite_count")
    val favoriteCount: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("item_count")
    val itemCount: Int?,
    @SerializedName("list_type")
    val listType: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster_path")
    val posterPath: String?
)