package com.elhady.movies.core.data.paging.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.data.mapper.tvshow.AiringTodayTvShowsDtoMapper
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.TvShowApiService
import javax.inject.Inject

class OnTheAirTVShowsPagingSource @Inject constructor(
    service: TvShowApiService,
    private val mapper: AiringTodayTvShowsDtoMapper
) : BasePagingSource<TvShowApiService, TvShows>(service) {

    override suspend fun fetchData(page: Int): List<TvShows> {
        val response = service.getOnTheAirTVShows(page).body()?.results?.filterNotNull()
        return response?.map { mapper.map(it) } ?: emptyList()
    }
}
