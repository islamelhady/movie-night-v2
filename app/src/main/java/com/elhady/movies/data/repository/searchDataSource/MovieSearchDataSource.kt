package com.elhady.movies.data.repository.searchDataSource

import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject
import kotlin.properties.Delegates

class MovieSearchDataSource @Inject constructor(private val service: MovieService) : BasePagingSource<MovieDto>() {

    private var querySearch by Delegates.notNull<String>()
    fun setQuerySearch(query: String) {
        querySearch = query
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val pageNumber = params.key ?: 1
        val response = service.searchForMovies(query = querySearch, page = pageNumber)
        val responsePage = response.body()

        return try {
            LoadResult.Page(
                data = response.body()?.items ?: emptyList(),
                prevKey = null,
                nextKey = if (responsePage?.items?.isEmpty() == true) null else responsePage?.page?.plus(1)
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}