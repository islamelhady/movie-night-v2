package com.elhady.movies.data.repository.mediaDataSource.series

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.data.remote.service.MovieService
import javax.inject.Inject

class LatestTVDataSource @Inject constructor(private val service: MovieService) :
    PagingSource<Int, TVShowDto>() {
    override fun getRefreshKey(state: PagingState<Int, TVShowDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShowDto> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getOnTheAirTV(page = pageNumber)

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