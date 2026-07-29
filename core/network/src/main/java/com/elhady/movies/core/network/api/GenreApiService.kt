package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.dto.common.GenresWrapperResponse
import com.elhady.movies.core.network.dto.common.GenreMovieRemoteDto
import com.elhady.movies.core.network.dto.common.GenreTvRemoteDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApiService {

    @GET("genre/movie/list")
    suspend fun getListOfGenresForMovies(
        @Query("language") language: String = "en"
    ): Response<GenresWrapperResponse<GenreMovieRemoteDto>>

    @GET("genre/tv/list")
    suspend fun getListOfGenresForTvs(
        @Query("language") language: String = "en"
    ): Response<GenresWrapperResponse<GenreTvRemoteDto>>
}
