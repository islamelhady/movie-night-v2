package com.elhady.movies.core.data.paging.movie

import com.elhady.movies.core.database.dao.GenreDao
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import com.elhady.movies.core.data.mapper.movie.DomainGenreMapper
import com.elhady.movies.core.data.mapper.account.DomainMyRatedMoviesMapper
import javax.inject.Inject

class RatedMoviesPagingSource @Inject constructor(
    service: AccountApiService,
    private val domainGenreMapper: DomainGenreMapper,
    private val mapper: DomainMyRatedMoviesMapper,
    private val genreDao: GenreDao,
) : BasePagingSource<AccountApiService, MyRatedMovie>(service) {

    override suspend fun fetchData(page: Int): List<MyRatedMovie> {
        val response = service.getRatedMovies(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(genreDao.getGenresMovies())
        return response?.map { mapper.map(it , genreMovieMapper) } ?: emptyList()
    }
}
