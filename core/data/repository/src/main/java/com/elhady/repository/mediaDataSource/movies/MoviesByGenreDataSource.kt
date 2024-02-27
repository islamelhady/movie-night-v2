package com.elhady.repository.mediaDataSource.movies

import com.elhady.remote.response.dto.MovieDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesByGenreDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<MovieDto>() {

    private var movieGenreID by Delegates.notNull<Int>()
    fun setGenre(genreId: Int) {
        movieGenreID = genreId
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {

        val pageNumber = params.key ?: 1
        val response = service.getMoviesListByGenre(genreID = movieGenreID, page = pageNumber)
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