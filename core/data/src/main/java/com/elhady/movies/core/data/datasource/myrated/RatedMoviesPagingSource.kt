package com.elhady.movies.core.data.datasource.myrated


import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.network.service.MovieService
import com.elhady.movies.core.network.BasePagingSource
import com.elhady.movies.core.common.domain.entities.myrated.MyRatedMovieEntity
import com.elhady.movies.core.data.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.mappers.domain.myrated.DomainMyRatedMoviesMapper
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
