package com.elhady.movies.data.repository.mediaDataSource.actors

import android.util.Log
import com.elhady.movies.data.remote.response.actor.PersonDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class ActorDataSource @Inject constructor(private val service: MovieService) : BasePagingSource<PersonDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonDto> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getPopularPerson(page = pageNumber)
            Log.d("Paging Number", pageNumber.toString())
            LoadResult.Page(
                data = response.body()?.items ?: emptyList(),
                prevKey = null,
                nextKey = response.body()?.page?.plus(1)
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}