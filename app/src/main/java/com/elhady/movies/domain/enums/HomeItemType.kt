package com.elhady.movies.domain.enums

enum class HomeItemType(val value: String) {
    TRENDING("Trending"),
    UPCOMING("Upcoming"),
    NOW_PLAYING("Now playing in cinema"),
    TOP_RATED("Top rated"),
    TOP_RATED_SERIES(""),
    ON_THE_AIR_SERIES("On the air TV"),
    MYSTERY("Mystery"),
    ADVENTURE("Adventure"),
    ACTOR_MOVIES("")
}