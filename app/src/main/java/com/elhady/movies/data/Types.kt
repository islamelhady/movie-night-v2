package com.elhady.movies.data

sealed class Types {
    object PopularMovieType : Types()
    object MovieType : Types()
    object CategoryType : Types()
    object ActorType : Types()
    object AiringTodayType : Types()
}
