package com.elhady.movies.core.data.datasource.showmore

import com.elhady.movies.core.common.domain.entities.MovieEntity
import com.elhady.movies.core.data.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.mappers.domain.movie.DomainTrendingMovieShowMoreMapper
import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.network.BasePagingSource
import com.elhady.movies.core.network.service.MovieService
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
