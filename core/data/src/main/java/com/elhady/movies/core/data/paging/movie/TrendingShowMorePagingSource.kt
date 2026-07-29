package com.elhady.movies.core.data.paging.movie

import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.core.data.mapper.movie.DomainGenreMapper
import com.elhady.movies.core.data.mapper.movie.DomainTrendingMovieShowMoreMapper
import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.MovieApiService
import javax.inject.Inject

class TrendingShowMorePagingSource @Inject constructor(
    service: MovieApiService,
    private val mapper: DomainTrendingMovieShowMoreMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val movieDao: MovieDao,
) : BasePagingSource<MovieApiService, MovieEntity>(service) {
    override suspend fun fetchData(page: Int): List<MovieEntity> {
        val response = service.getTrendingMovies(page = page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(movieDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}
