package com.elhady.movies.core.domain.usecase.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.entities.TVShowsEntity

interface TvShowRepository {
    suspend fun refreshTvShows()
    suspend fun getTvShowsFromDatabase(): List<TVShowsEntity>
    suspend fun refreshAiringTodayTvShows()
    suspend fun getAiringTodayTvShowsFromDatabase(): List<TVShowsEntity>
    suspend fun getAiringTodayTVShowsFromRemote(): List<TVShowsEntity>
    suspend fun getAiringTodayTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getTopRatedTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getPopularTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getOnTheAirTVShowsPager(): Pager<Int, TVShowsEntity>

}