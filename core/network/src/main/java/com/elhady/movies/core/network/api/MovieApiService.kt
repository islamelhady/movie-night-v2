package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.dto.movie.RatingRequest
import com.elhady.movies.core.network.dto.common.DataWrapperResponse
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.network.dto.common.StatusResponse
import com.elhady.movies.core.network.dto.common.YoutubeVideoDetailsDto
import com.elhady.movies.core.network.dto.movie.MovieDetailsDto
import com.elhady.movies.core.network.dto.movie.ReviewsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MovieDto>>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MovieDto>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MovieDto>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MovieDto>>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Query("page") page: Int = 1,
        @Path("movie_id") movieId: Int
    ): Response<DataWrapperResponse<MovieDto>>

    @GET("movie/latest")
    suspend fun getLatestMovie(): Response<MovieDto>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MovieDto>>

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerVideoForMovie(
        @Path("movie_id") tvShowId: Int
    ): Response<DataWrapperResponse<YoutubeVideoDetailsDto>>

    @GET("movie/{movieId}?&append_to_response=videos,credits,recommendations,reviews")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): Response<MovieDetailsDto>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movieId}/rating")
    suspend fun setMovieRate(
        @Body ratingRequest: RatingRequest,
        @Path("movieId") movieId: Int
    ): Response<StatusResponse>

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1
    ): Response<ReviewsDto>
}
