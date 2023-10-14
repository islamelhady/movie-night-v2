package com.elhady.movies.data.remote.service

import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.response.genre.GenreResponse
import com.elhady.movies.data.remote.response.login.RequestTokenResponse
import com.elhady.movies.data.remote.response.login.SessionResponse
import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.domain.TrendingTimeWindow
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<BaseResponse<MovieDto>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<BaseResponse<MovieDto>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<BaseResponse<MovieDto>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<BaseResponse<MovieDto>>

    @GET("trending/person/{time_window}")
    suspend fun getTrendingPerson(@Path("time_window") timeWindow: String = TrendingTimeWindow.DAY.value): Response<BaseResponse<PersonDto>>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovie(@Path("time_window") timeWindow: String = TrendingTimeWindow.WEEK.value): Response<BaseResponse<MovieDto>>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTV(): Response<BaseResponse<TVShowDto>>

    @GET("authentication/token/new")
    suspend fun getRequestToken(): Response<RequestTokenResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestTokenWithLogin(@FieldMap body: Map<String, Any>): Response<RequestTokenResponse>


    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSession(@Field("request_token") requestToken: String): Response<SessionResponse>

    @GET("genre/movie/list")
    suspend fun getGenreMovies(): Response<GenreResponse>
}

