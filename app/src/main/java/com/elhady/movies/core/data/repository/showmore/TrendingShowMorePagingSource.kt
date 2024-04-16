package com.elhady.movies.core.data.repository.showmore

import com.elhady.movies.core.data.local.database.MovieDao
import com.elhady.movies.core.data.remote.service.MovieService
import com.elhady.movies.core.data.repository.BasePagingSource
import com.elhady.movies.core.data.repository.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainTrendingMovieShowMoreMapper
import com.elhady.movies.core.domain.entities.MovieEntity
import javax.inject.Inject

class TrendingShowMorePagingSource @Inject constructor(
    service: MovieService,
    private val mapper: DomainTrendingMovieShowMoreMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val movieDao: MovieDao,
) : BasePagingSource<MovieEntity>(service) {
    override suspend fun fetchData(page: Int): List<MovieEntity> {
        val response = service.getTrendingMovies(page = page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(movieDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}