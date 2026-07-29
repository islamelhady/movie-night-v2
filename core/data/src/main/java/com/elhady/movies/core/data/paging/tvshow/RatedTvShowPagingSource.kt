package com.elhady.movies.core.data.paging.tvshow

import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.data.mapper.movie.DomainGenreMapper
import com.elhady.movies.core.data.mapper.account.DomainMyRatedTvShowMapper
import com.elhady.movies.core.database.dao.GenreDao
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.AccountApiService
import javax.inject.Inject

class RatedTvShowPagingSource @Inject constructor(
    service: AccountApiService,
    private val domainGenreMapper: DomainGenreMapper,
    private val mapper: DomainMyRatedTvShowMapper,
    private val genreDao: GenreDao,
) : BasePagingSource<AccountApiService, MyRatedTvShow>(service) {

    override suspend fun fetchData(page: Int): List<MyRatedTvShow> {
        val response = service.getRatedTv(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(genreDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}
