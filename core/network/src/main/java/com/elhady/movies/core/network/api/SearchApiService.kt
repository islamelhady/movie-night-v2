package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.dto.common.DataWrapperResponse
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.network.dto.people.PeopleDto
import com.elhady.movies.core.network.dto.tvshow.TvDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("search/movie")
    suspend fun searchForMovies(
        @Query("query") query: String,
        @Query("year") year: Int? = null,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("region") region: String? = null,
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<MovieDto>>

    @GET("search/tv")
    suspend fun searchForTv(
        @Query("query") query: String,
        @Query("year") year: Int? = null,
        @Query("first_air_date_year") firstAirDateYear: String? = null,
        @Query("region") region: String? = null,
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<TvDto>>

    @GET("search/person")
    suspend fun searchForPeople(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<PeopleDto>>
}
