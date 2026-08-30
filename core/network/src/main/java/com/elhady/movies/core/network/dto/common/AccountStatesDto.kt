package com.elhady.movies.core.network.dto.common

import com.google.gson.annotations.SerializedName

data class AccountStatesDto(
    @SerializedName("favorite")
    val favorite: Boolean? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("rated")
    val rated: Any? = null,
    @SerializedName("watchlist")
    val watchlist: Boolean? = null
)
