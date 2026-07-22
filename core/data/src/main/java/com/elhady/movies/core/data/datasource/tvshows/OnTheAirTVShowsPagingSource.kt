package com.elhady.movies.core.data.datasource.tvshows

import com.elhady.movies.core.common.domain.entities.TVShowsEntity
import com.elhady.movies.core.data.mappers.domain.tv.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.network.BasePagingSource
import com.elhady.movies.core.network.service.MovieService
import javax.inject.Inject

class OnTheAirTVShowsPagingSource @Inject constructor(
    service: MovieService,
    private val mapper: DomainAiringTodayTvShowsMapper
) : BasePagingSource<TVShowsEntity>(service) {

    override suspend fun fetchData(page: Int): List<TVShowsEntity> {
        val response = service.getOnTheAirTVShows(page).body()?.results?.filterNotNull()
        return response?.map { mapper.map(it) } ?: emptyList()
    }
}
