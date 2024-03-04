package com.elhady.repository.showMore

import com.elhady.entities.MovieEntity
import com.elhady.local.database.dao.MovieDao
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.BasePagingSource
import com.elhady.repository.mappers.domain.DomainGenreMapper
import com.elhady.repository.mappers.domain.movie.DomainPopularMovieMapperShowMore
import javax.inject.Inject

class PopularMoviesShowMorePagingSource @Inject constructor(
    service: MovieService,
    private val mapper: DomainPopularMovieMapperShowMore,
    private val domainGenreMapper: DomainGenreMapper,
    private val movieDao: MovieDao,

    ) : BasePagingSource<MovieEntity>(service) {

    override suspend fun fetchData(page: Int): List<MovieEntity> {
        val response = service.getPopularMovies(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(movieDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}