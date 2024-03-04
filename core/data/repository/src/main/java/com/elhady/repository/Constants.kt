package com.elhady.repository

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "282157b63b2a2ef81abaca304a648cba"
    const val IMAGE_PATH = "https://image.tmdb.org/t/p/w500"
    const val TMDB_SIGNUP_URL = "https://www.themoviedb.org/signup"

    const val ITEMS_PER_PAGE = 50
    const val MYSTERY_ID = 9648
    const val ADVENTURE_ID = 12
    const val FIRST_CATEGORY_ID = 0
    const val FIRST_CATEGORY_NAME = "All"

    const val MAX_NUM_REVIEWS = 3

    const val INTERNET_STATUS = 400

    const val TV_SERIES_SHOW = "tv"
    const val MOVIE = "movie"
    const val PERSON ="person"

    /**
     *  Movies
     */
    const val POPULAR_MOVIE_REQUEST_DATE_KEY = "popular_movie_request_date"
    const val TRENDING_MOVIE_REQUEST_DATE_KEY = "trending_movie_request_date"
    const val UPCOMING_MOVIE_REQUEST_DATE_KEY = "upcoming_movie_request_date"
    const val NOW_PLAYING_MOVIE_REQUEST_DATE_KEY = "now_playing_movie_request_date"
    const val TOP_RATED_MOVIE_REQUEST_DATE_KEY = "top_rated_movie_request_date"
    const val MYSTERY_MOVIE_REQUEST_DATE_KEY = "mystery_movie_request_date"
    const val ADVENTURE_MOVIE_REQUEST_DATE_KEY = "adventure_movie_request_date"

    /**
     *  Series
     */
    const val ON_THE_AIR_SERIES_REQUEST_DATE_KEY = "on_the_air_series_request_date"
    const val AIRING_TODAY_SERIES_REQUEST_DATE_KEY = "air_today_series_request_date"
    const val TV_SERIES_LISTS_REQUEST_DATE_KEY = "tv_series_lists_request_date"

    /**
     *  Actors
     */
    const val POPULAR_PERSON_REQUEST_DATE_KEY = "popular_persons_request_date"

}