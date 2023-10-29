package com.elhady.movies.data.repository.mediaDataSource.movies

import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class TrendingMovieDataSource @Inject constructor(private val service: MovieService) : BasePagingSource<MovieDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val pageNumber = params.key ?: 1

        return try {
            val response = service.getTrendingMovie(page = pageNumber)

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