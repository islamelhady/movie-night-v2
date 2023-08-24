package com.elhady.movies.data

sealed class Types {
    object BannerType : Types()
    object MovieType : Types()
    object CategoryType : Types()
}
