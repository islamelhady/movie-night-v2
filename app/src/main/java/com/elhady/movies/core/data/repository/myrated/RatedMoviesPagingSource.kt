package com.elhady.movies.core.data.repository.myrated


import com.elhady.movies.core.data.local.database.MovieDao
import com.elhady.movies.core.data.remote.service.MovieService
import com.elhady.movies.core.data.repository.BasePagingSource
import com.elhady.movies.core.data.repository.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.repository.mappers.domain.my_rated.DomainMyRatedMoviesMapper
import com.elhady.movies.core.domain.entities.myrated.MyRatedMovieEntity
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