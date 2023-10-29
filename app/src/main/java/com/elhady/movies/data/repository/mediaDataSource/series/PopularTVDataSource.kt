package com.elhady.movies.data.repository.mediaDataSource.series

import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class PopularTVDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<TVShowDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShowDto> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getPopularTV()
            LoadResult.Page(
                data = response.body()?.items ?: emptyList(),
                prevKey = null,
                nextKey = response.body()?.page
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}