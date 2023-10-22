package com.elhady.movies.data.repository.mediaDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.service.MovieService
import javax.inject.Inject

class ActorDataSource @Inject constructor(private val service: MovieService) : PagingSource<Int, PersonDto>() {
    override fun getRefreshKey(state: PagingState<Int, PersonDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonDto> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getTrendingPerson(page = pageNumber)
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