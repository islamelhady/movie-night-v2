package com.elhady.movies.core.data.repository.showmore

import com.elhady.movies.core.data.local.database.MovieDao
import com.elhady.movies.core.data.remote.service.MovieService
import com.elhady.movies.core.data.repository.BasePagingSource
import com.elhady.movies.core.data.repository.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainTopRatedMoviesShowMoreMapper
import com.elhady.movies.core.domain.entities.MovieEntity
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