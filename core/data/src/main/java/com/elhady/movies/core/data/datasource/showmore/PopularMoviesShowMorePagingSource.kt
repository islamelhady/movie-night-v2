package com.elhady.movies.core.data.datasource.showmore

import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.data.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.mappers.domain.movie.DomainPopularMovieShowMoreMapper
import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.data.bases.BasePagingSource
import com.elhady.movies.core.network.service.MovieService
import javax.inject.Inject

class PopularMoviesShowMorePagingSource @Inject constructor(
    service: MovieService,
    private val mapper: DomainPopularMovieShowMoreMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val movieDao: MovieDao,
) : BasePagingSource<MovieEntity>(service) {

    override suspend fun fetchData(page: Int): List<MovieEntity> {
        val response = service.getPopularMovies(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(movieDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}
