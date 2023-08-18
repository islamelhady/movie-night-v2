package com.elhady.movies.data.remote.service

import com.elhady.movies.data.remote.AuthInterceptor
import com.elhady.movies.data.remote.response.MovieResponse
import com.elhady.utilities.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>

}

object ApiRequest {
    private val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(MovieService::class.java)
}