package com.elhady.movies.core.data.repository.tvshows

import com.elhady.movies.core.data.remote.service.MovieService
import com.elhady.movies.core.data.repository.BasePagingSource
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.domain.entities.TVShowsEntity
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