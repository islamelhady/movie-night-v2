package com.elhady.repository.mediaDataSource.movies

import com.elhady.remote.response.dto.MovieRemoteDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class UpcomingMovieDataSource @Inject constructor(private val service: MovieService) : BasePagingSource<MovieRemoteDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieRemoteDto> {
        val pageNumber = params.key ?: 1

        return try {
            val response = service.getUpcomingMovies(page = pageNumber)

            LoadResult.Page(
                data = response.body()?.result ?: emptyList(),
                prevKey = null,
                nextKey = response.body()?.page?.plus(1)
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}