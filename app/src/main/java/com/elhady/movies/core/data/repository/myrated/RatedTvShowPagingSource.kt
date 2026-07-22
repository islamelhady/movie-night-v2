package com.elhady.movies.core.data.repository.myrated

import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.network.service.MovieService
import com.elhady.movies.core.network.BasePagingSource
import com.elhady.movies.core.data.repository.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.repository.mappers.domain.myrated.DomainMyRatedTvShowMapper
import com.elhady.movies.core.common.domain.entities.myrated.MyRatedTvShowEntity
import javax.inject.Inject

class RatedTvShowPagingSource @Inject constructor(
    service: MovieService,
    private val domainGenreMapper: DomainGenreMapper,
    private val mapper: DomainMyRatedTvShowMapper,
    private val movieDao: MovieDao,
) : BasePagingSource<MyRatedTvShowEntity>(service){
    override suspend fun fetchData(page: Int): List<MyRatedTvShowEntity> {
        val response = service.getRatedTv(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(movieDao.getGenresMovies())
        return response?.map { mapper.map(it , genreMovieMapper) } ?: emptyList()
    }
}
