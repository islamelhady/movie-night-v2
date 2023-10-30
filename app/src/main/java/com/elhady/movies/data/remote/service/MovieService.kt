package com.elhady.movies.data.remote.service

import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.CreditsDto
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.actor.PersonDto
import com.elhady.movies.data.remote.response.actor.MovieCreditsDto
import com.elhady.movies.data.remote.response.genre.GenreResponse
import com.elhady.movies.data.remote.response.login.RequestTokenResponse
import com.elhady.movies.data.remote.response.login.SessionResponse
import com.elhady.movies.data.remote.response.movie.MovieDetailsDto
import com.elhady.movies.data.remote.response.review.ReviewDto
import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.domain.TrendingTimeWindow
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     *  MOVIE LISTS
     * * Popular
     * * Upcoming
     * * Top Rated
     * * Now Playing
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>


    /**
     *  Trending
     * * Movies
     * * People
     */
    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovie(@Path("time_window") timeWindow: String = TrendingTimeWindow.WEEK.value, @Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("trending/person/{time_window}")
    suspend fun getTrendingPerson(
        @Path("time_window") timeWindow: String = TrendingTimeWindow.DAY.value,
        @Query("page") page: Int = 1
    ): Response<BaseResponse<PersonDto>>


    /**
     *  PEOPLE LISTS
     */
    @GET("person/popular")
    suspend fun getPopularPerson(@Query("page") page: Int = 1): Response<BaseResponse<PersonDto>>

    @GET("person/{person_id}")
    suspend fun getPersonsDetails(@Path("person_id") actorID: Int): Response<PersonDto>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovies(@Path("person_id") actorID: Int): Response<MovieCreditsDto>

    /**
     *  GENRES
     * * Movies
     */
    @GET("genre/movie/list")
    suspend fun getGenreMovies(): Response<GenreResponse>

    /**
     * Movies
     * * Details
     * * Credits (Cast)
     * * Similar
     */
    @GET("movie/{movie_id}")
    suspend fun getDetailsMovies(@Path("movie_id") movieId: Int): Response<MovieDetailsDto>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(@Path("movie_id") movieId: Int): Response<CreditsDto>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(@Path("movie_id") movieId: Int): Response<BaseResponse<MovieDto>>


    /**
     *  Review
     * * movie
     */
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(@Path("movie_id") movieId: Int): Response<BaseResponse<ReviewDto>>


    /**
     *   TV SERIES LISTS
     * * Airing Today
     * * On The Air
     * * Popular
     * * Top Rated
     */
    @GET("tv/airing_today")
    suspend fun getAiringTodayTV(): Response<BaseResponse<TVShowDto>>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTV(@Query("page") page: Int = 1): Response<BaseResponse<TVShowDto>>

    @GET("tv/popular")
    suspend fun getPopularTV(@Query("page") page: Int =1): Response<BaseResponse<TVShowDto>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(@Query("page") page: Int = 1): Response<BaseResponse<TVShowDto>>

    /**
     *   DISCOVER
     * * Adventure Movies
     * * Mystery Movies
     */
    @GET("discover/movie")
    suspend fun getMoviesListByGenre(
        @Query("with_genres") genreID: Int,
        @Query("page") page: Int = 1
    ): Response<BaseResponse<MovieDto>>


    /**
     *  AUTHENTICATION
     * * Create Request Token
     * * Create Session (with login)
     * * Create Session
     */
    @GET("authentication/token/new")
    suspend fun getRequestToken(): Response<RequestTokenResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestTokenWithLogin(@FieldMap body: Map<String, Any>): Response<RequestTokenResponse>

    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSession(@Field("request_token") requestToken: String): Response<SessionResponse>

}


