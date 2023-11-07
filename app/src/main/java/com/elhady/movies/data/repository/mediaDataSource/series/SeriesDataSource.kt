package com.elhady.movies.data.repository.mediaDataSource.series

import com.elhady.movies.data.remote.response.series.SeriesDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class SeriesDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<SeriesDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesDto> {
        val pageNumber = params.key ?: 1
        val response = service.getAllSeries(page = pageNumber)

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