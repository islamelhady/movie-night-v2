package com.elhady.movies.data.repository.mediaDataSource.series

import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class TopRatedTVDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<TVShowDto>() {

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