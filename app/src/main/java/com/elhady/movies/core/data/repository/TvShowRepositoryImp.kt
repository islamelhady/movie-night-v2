package com.elhady.movies.core.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.data.local.database.TvShowDao
import com.elhady.movies.core.data.local.database.dto.tvshow.TvShowsLocalDto
import com.elhady.movies.core.data.remote.service.MovieService
import com.elhady.movies.core.data.repository.mappers.cash.tv.LocalAiringTodayTvShowMapper
import com.elhady.movies.core.data.repository.mappers.cash.tv.LocalTvShowMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainAiringTodayTVMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainTVMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.data.repository.tv_shows.AiringTodayTVShowsPagingSource
import com.elhady.movies.core.data.repository.tv_shows.OnTheAirTVShowsPagingSource
import com.elhady.movies.core.data.repository.tv_shows.PopularTVShowsPagingSource
import com.elhady.movies.core.data.repository.tv_shows.TopRatedTVShowsPagingSource
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.usecase.repository.ApiThrowable
import com.elhady.movies.core.domain.usecase.repository.TvShowRepository
import java.util.Random
import javax.inject.Inject

class TvShowRepositoryImp @Inject constructor(
    private val service: MovieService,
    private val random: Random,
    private val localTvShowMapper: LocalTvShowMapper,
    private val domainTvShowMapper: DomainTVMapper,
    private val tvShowDao: TvShowDao,
    private val airingTodayTvShowsPagingSource: AiringTodayTVShowsPagingSource,
    private val topRatedTvShowsPagingSource: TopRatedTVShowsPagingSource,
    private val onTheAirTVShowsPagingSource: OnTheAirTVShowsPagingSource,
    private val popularTVShowsPagingSource: PopularTVShowsPagingSource,
    private val domainAiringTodayTvShowsMapper: DomainAiringTodayTvShowsMapper,
    private val localAiringTodayTvShowMapper: LocalAiringTodayTvShowMapper,
    private val domainAiringTodayTVMapper: DomainAiringTodayTVMapper
) : TvShowRepository, BaseRepository() {

    // region Airing Today
    override suspend fun refreshAiringTodayTvShows() {
        refreshWrapper(
            apiCall = { service.getAiringTodayTVShows(random.nextInt(20) + 1) },
            localMapper = localAiringTodayTvShowMapper::map,
            databaseSaver = tvShowDao::insertAiringTodayTvShow,
            clearOldLocalData = tvShowDao::clearAllAiringTodayTvShow
        )
    }

    override suspend fun getAiringTodayTvShowsFromDatabase(): List<TVShowsEntity> {
        Log.d("dao TVREPOSITORY" , "Airing Dao : ${tvShowDao.getAllTvShow().size}")
        return domainAiringTodayTVMapper.map(tvShowDao.getAllAiringTodayTvShow())
    }

    override suspend fun getAiringTodayTVShowsFromRemote(): List<TVShowsEntity> {
        val page = random.nextInt(500) + 1
        val airingTodayRemoteDTOs =
            wrapApiCall { service.getAiringTodayTVShows(page = page) }.results?.filterNotNull() ?: emptyList()
        Log.d("remote TVREPOSITORY" , "${airingTodayRemoteDTOs.size}")
        return domainAiringTodayTvShowsMapper.map(airingTodayRemoteDTOs)
    }

    // endregion

    override suspend fun getTvShowsFromDatabase(): List<TVShowsEntity> {
        return domainTvShowMapper.map(tvShowDao.getAllTvShow())
    }

    override suspend fun refreshTvShows() {
        try {
            val items = mutableListOf<TvShowsLocalDto>()
            service.getAiringTodayTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            service.getTopRatedTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            service.getPopularTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            service.getOnTheAirTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            tvShowDao.clearAllTvShow()
            tvShowDao.insertTvShow(items)

        } catch (throwable: Throwable) {
            throw ApiThrowable(throwable.message)
        }
    }



    override suspend fun getAiringTodayTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { airingTodayTvShowsPagingSource }
        )
    }

    override suspend fun getTopRatedTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedTvShowsPagingSource }
        )
    }

    override suspend fun getPopularTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { popularTVShowsPagingSource }
        )
    }

    override suspend fun getOnTheAirTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { onTheAirTVShowsPagingSource }
        )
    }

}