package com.elhady.movies.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.service.MovieService
import javax.inject.Inject

class UpcomingMovieDataSource @Inject constructor(private val service: MovieService) : PagingSource<Int, MovieDto>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val pageNumber = params.key ?: 1

        return try {
            val response = service.getUpcomingMovies(page = pageNumber)

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