package com.elhady.movies.core.data.paging.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.data.mapper.tvshow.AiringTodayTvShowDtoMapper
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.TvShowApiService
import com.elhady.movies.core.network.exception.SafeApiCaller
import javax.inject.Inject

class PopularTvShowPagingSource @Inject constructor(
    private val service: TvShowApiService,
    private val safeApiCaller: SafeApiCaller,
    private val mapper: AiringTodayTvShowDtoMapper
) : BasePagingSource<TvShowApiService, TvShows>() {

    override suspend fun fetchData(page: Int): List<TvShows> {
        val response = safeApiCaller.execute { service.getPopularTvShows(page) }
        return response.results?.filterNotNull()?.map { mapper.map(it) }.orEmpty()
    }
}
