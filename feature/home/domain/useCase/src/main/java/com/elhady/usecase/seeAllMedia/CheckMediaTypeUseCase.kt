package com.elhady.usecase.seeAllMedia

import javax.inject.Inject

class CheckMediaTypeUseCase @Inject constructor() {
    operator fun invoke(type: SeeAllType): Boolean {
        return (type == SeeAllType.ON_THE_AIR_TV
                || type == SeeAllType.POPULAR_TV
                || type == SeeAllType.TOP_RATED_TV
                || type == SeeAllType.LATEST_TV
                )
    }
}

enum class SeeAllType(val value: String) {
    TOP_RATED_TV("Top Rated Series"),
    POPULAR_TV("Popular Series"),
    LATEST_TV("Latest Series"),
    ON_THE_AIR_TV("On The Air Series"),
    UPCOMING_MOVIE("Upcoming Movies"),
    TRENDING_MOVIE("Trending Movies"),
    NOW_PLAYING_MOVIE("Now Playing in Cinema"),
    TOP_RATED_MOVIE("Top Rated Movies"),
    MYSTERY_MOVIE("Mystery Movies"),
    ADVENTURE_MOVIE("Adventure Movies"),
    ACTOR_MOVIES("Actor Movies")
}