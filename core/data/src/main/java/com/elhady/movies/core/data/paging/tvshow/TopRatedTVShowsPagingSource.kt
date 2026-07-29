package com.elhady.movies.core.data.paging.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.data.mapper.tvshow.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.TvShowApiService
import javax.inject.Inject

class TopRatedTVShowsPagingSource @Inject constructor(
    service: TvShowApiService,
    private val mapper: DomainAiringTodayTvShowsMapper
) : BasePagingSource<TvShowApiService, TvShows>(service) {

    override suspend fun fetchData(page: Int): List<TvShows> {
        val response = service.getTopRatedTVShows(page).body()?.results?.filterNotNull()
        return response?.map { mapper.map(it) } ?: emptyList()
    }
}
