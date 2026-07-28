package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.model.response.DataWrapperResponse
import com.elhady.movies.core.network.model.response.dto.MoviesByPeopleResponse
import com.elhady.movies.core.network.model.response.dto.PeopleDetailsResponse
import com.elhady.movies.core.network.model.response.dto.PeopleRemoteDto
import com.elhady.movies.core.network.model.response.dto.TvShowsByPeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApiService {

    @GET("person/popular")
    suspend fun getPopularPeople(@Query("page") page: Int = 1): Response<DataWrapperResponse<PeopleRemoteDto>>

    @GET("person/{person_id}")
    suspend fun getPerson(@Path("person_id") personId: Int): Response<PeopleDetailsResponse>

    @GET("person/{person_id}/movie_credits")
    suspend fun getMoviesByPerson(@Path("person_id") personId: Int): Response<MoviesByPeopleResponse>

    @GET("person/{person_id}/tv_credits")
    suspend fun getTvShowsByPerson(@Path("person_id") personId: Int): Response<TvShowsByPeopleResponse>
}
