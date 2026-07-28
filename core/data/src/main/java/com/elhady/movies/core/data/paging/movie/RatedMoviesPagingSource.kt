package com.elhady.movies.core.data.paging.movie

import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.network.service.MovieService
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.domain.model.account.MyRatedMovieEntity
import com.elhady.movies.core.data.mapper.movie.DomainGenreMapper
import com.elhady.movies.core.data.mapper.account.DomainMyRatedMoviesMapper
import javax.inject.Inject

class RatedMoviesPagingSource @Inject constructor(
    service: MovieService,
    private val domainGenreMapper: DomainGenreMapper,
    private val mapper: DomainMyRatedMoviesMapper,
    private val movieDao: MovieDao,
) : BasePagingSource<MyRatedMovieEntity>(service) {

    override suspend fun fetchData(page: Int): List<MyRatedMovieEntity> {
        val response = service.getRatedMovies(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(movieDao.getGenresMovies())
        return response?.map { mapper.map(it , genreMovieMapper) } ?: emptyList()
    }
}
