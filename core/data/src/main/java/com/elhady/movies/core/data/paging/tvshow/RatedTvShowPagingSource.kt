package com.elhady.movies.core.data.paging.tvshow

import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.data.mapper.movie.GenreEntityMapper
import com.elhady.movies.core.data.mapper.account.MyRatedTvShowDtoMapper
import com.elhady.movies.core.database.dao.genre.GenreMovieDao
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.network.exception.SafeApiCaller
import javax.inject.Inject

class RatedTvShowPagingSource @Inject constructor(
    private val service: AccountApiService,
    private val safeApiCaller: SafeApiCaller,
    private val domainGenreMapper: GenreEntityMapper,
    private val mapper: MyRatedTvShowDtoMapper,
    private val genreMovieDao: GenreMovieDao,
) : BasePagingSource<AccountApiService, MyRatedTvShow>() {

    override suspend fun fetchData(page: Int): List<MyRatedTvShow> {
        val response = safeApiCaller.execute { service.getRatedTv(page) }.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(genreMovieDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) }.orEmpty()
    }
}
