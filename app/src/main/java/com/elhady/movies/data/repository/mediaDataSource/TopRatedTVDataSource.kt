package com.elhady.movies.data.repository.mediaDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.data.remote.service.MovieService
import javax.inject.Inject

class TopRatedTVDataSource @Inject constructor(private val service: MovieService) :
    PagingSource<Int, TVShowDto>() {
    override fun getRefreshKey(state: PagingState<Int, TVShowDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShowDto> {
        val pageNumber = params.key ?: 1
        val response = service.getTopRatedTV(page = pageNumber)

        return try {
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