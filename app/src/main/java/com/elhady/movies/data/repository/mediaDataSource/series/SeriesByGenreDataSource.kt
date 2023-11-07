package com.elhady.movies.data.repository.mediaDataSource.series

import com.elhady.movies.data.remote.response.series.SeriesDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject
import kotlin.properties.Delegates

class SeriesByGenreDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<SeriesDto>() {

    private var seriesGenreId by Delegates.notNull<Int>()
    fun setGenre(genreId: Int) {
        seriesGenreId = genreId
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesDto> {
        val pageNumber = params.key ?: 1
        val response = service.getSeriesByGenre(genreID = seriesGenreId, page = pageNumber)

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