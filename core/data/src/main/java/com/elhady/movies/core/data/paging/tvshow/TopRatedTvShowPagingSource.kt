package com.elhady.movies.core.data.paging.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.data.mapper.tvshow.AiringTodayTvShowDtoMapper
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.TvShowApiService
import javax.inject.Inject

class TopRatedTvShowPagingSource @Inject constructor(
    service: TvShowApiService,
    private val mapper: AiringTodayTvShowDtoMapper
) : BasePagingSource<TvShowApiService, TvShows>(service) {

    override suspend fun fetchData(page: Int): List<TvShows> {
        val response = service.getTopRatedTvShows(page).body()?.results?.filterNotNull()
        return response?.map { mapper.map(it) } ?: emptyList()
    }
}
