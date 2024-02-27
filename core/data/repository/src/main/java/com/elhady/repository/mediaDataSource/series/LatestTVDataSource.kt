package com.elhady.repository.mediaDataSource.series

import com.elhady.remote.response.series.SeriesDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class LatestTVDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<SeriesDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesDto> {
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