package com.elhady.movies.core.data.paging.tvshow

import com.elhady.movies.core.domain.model.tvshow.TVShowsEntity
import com.elhady.movies.core.data.mapper.tvshow.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.service.MovieService
import javax.inject.Inject

class PopularTVShowsPagingSource @Inject constructor(
    service: MovieService,
    private val mapper: DomainAiringTodayTvShowsMapper
) : BasePagingSource<TVShowsEntity>(service) {

    override suspend fun fetchData(page: Int): List<TVShowsEntity> {
        val response = service.getPopularTVShows(page).body()?.results?.filterNotNull()
        return response?.map { mapper.map(it) } ?: emptyList()
    }
}
