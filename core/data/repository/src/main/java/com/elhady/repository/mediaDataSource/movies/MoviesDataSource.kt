package com.elhady.repository.mediaDataSource.movies

import com.elhady.remote.response.dto.MovieDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class MoviesDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<MovieDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val pageNumber = params.key ?: 1
        val response = service.getAllMovies(page = pageNumber)

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