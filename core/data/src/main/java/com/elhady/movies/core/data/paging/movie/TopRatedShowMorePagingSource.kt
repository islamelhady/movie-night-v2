package com.elhady.movies.core.data.paging.movie

import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.data.mapper.movie.DomainGenreMapper
import com.elhady.movies.core.data.mapper.movie.DomainTopRatedMoviesShowMoreMapper
import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.service.MovieService
import javax.inject.Inject

class TopRatedShowMorePagingSource @Inject constructor(
    service: MovieService,
    private val mapper: DomainTopRatedMoviesShowMoreMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val movieDao: MovieDao,
) : BasePagingSource<MovieEntity>(service) {

    override suspend fun fetchData(page: Int): List<MovieEntity> {
        val response = service.getTopRatedMovies(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(movieDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}
