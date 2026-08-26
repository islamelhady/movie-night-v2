package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.dto.common.DataWrapperResponse
import com.elhady.movies.core.network.dto.people.MovieByPeopleDto
import com.elhady.movies.core.network.dto.people.PeopleDetailsDto
import com.elhady.movies.core.network.dto.people.PeopleDto
import com.elhady.movies.core.network.dto.people.TvShowByPeopleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApiService {

    @GET("person/popular")
    suspend fun getPopularPeople(@Query("page") page: Int = 1): Response<DataWrapperResponse<PeopleDto>>

    @GET("person/{person_id}")
    suspend fun getPerson(@Path("person_id") personId: Int): Response<PeopleDetailsDto>

    @GET("person/{person_id}/movie_credits")
    suspend fun getMoviesByPerson(@Path("person_id") personId: Int): Response<MovieByPeopleDto>

    @GET("person/{person_id}/tv_credits")
    suspend fun getTvShowsByPerson(@Path("person_id") personId: Int): Response<TvShowByPeopleDto>
}
