package com.elhady.movies.core.data.paging.movie

import com.elhady.movies.core.database.dao.genre.GenreMovieDao
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import com.elhady.movies.core.data.mapper.movie.GenreEntityMapper
import com.elhady.movies.core.data.mapper.account.MyRatedMoviesDtoMapper
import com.elhady.movies.core.network.exception.SafeApiCaller
import javax.inject.Inject

class RatedMoviesPagingSource @Inject constructor(
    private val service: AccountApiService,
    private val safeApiCaller: SafeApiCaller,
    private val domainGenreMapper: GenreEntityMapper,
    private val mapper: MyRatedMoviesDtoMapper,
    private val genreMovieDao: GenreMovieDao,
) : BasePagingSource<AccountApiService, MyRatedMovie>() {

    override suspend fun fetchData(page: Int): List<MyRatedMovie> {
        val response = safeApiCaller.execute { service.getRatedMovies(page) }.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(genreMovieDao.getGenresMovies())
        return response?.map { mapper.map(it , genreMovieMapper) }.orEmpty()
    }
}
