package com.elhady.movies.data.repository.mediaDataSource.movies

import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesByGenreDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<MovieDto>() {

    private var mediaGenreID by Delegates.notNull<Int>()
    fun setGenre(genreId: Int) {
        mediaGenreID = genreId
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {

        val pageNumber = params.key ?: 1
        val response = service.getMoviesListByGenre(genreID = mediaGenreID, page = pageNumber)
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