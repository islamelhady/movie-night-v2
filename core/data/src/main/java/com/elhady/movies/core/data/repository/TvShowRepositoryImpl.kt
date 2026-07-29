package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.common.ApiThrowable
import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.common.DomainStatusMapper
import com.elhady.movies.core.data.mapper.common.DomainYoutubeDetailsMapper
import com.elhady.movies.core.data.mapper.episode.DomainCastMapper
import com.elhady.movies.core.data.mapper.episode.DomainEpisodeDetailsMapper
import com.elhady.movies.core.data.mapper.episode.DomainRatingEpisodeMapper
import com.elhady.movies.core.data.mapper.people.DomainTvShowsByPeopleMapper
import com.elhady.movies.core.data.mapper.season.DomainSeasonDetailsMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainAiringTodayTVMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainMyRatedTvShowDetailsMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTVMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsCreditMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsReviewMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsSeasonMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvShowMapper
import com.elhady.movies.core.data.mapper.tvshow.LocalAiringTodayTvShowMapper
import com.elhady.movies.core.data.mapper.tvshow.LocalTvShowMapper
import com.elhady.movies.core.data.paging.tvshow.AiringTodayTVShowsPagingSource
import com.elhady.movies.core.data.paging.tvshow.OnTheAirTVShowsPagingSource
import com.elhady.movies.core.data.paging.tvshow.PopularTVShowsPagingSource
import com.elhady.movies.core.data.paging.tvshow.RatedTvShowPagingSource
import com.elhady.movies.core.data.paging.tvshow.TopRatedTVShowsPagingSource
import com.elhady.movies.core.database.dao.tv.AiringTodayTvShowDao
import com.elhady.movies.core.database.dao.tv.TvShowDao
import com.elhady.movies.core.database.entity.tvshow.TvShowEntity
import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.EpisodeDetails
import com.elhady.movies.core.domain.model.tvshow.RatingEpisodeDetailsStatus
import com.elhady.movies.core.domain.model.tvshow.SeasonDetails
import com.elhady.movies.core.domain.model.tvshow.Season
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import com.elhady.movies.core.domain.repository.TvShowRepository
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.network.api.PeopleApiService
import com.elhady.movies.core.network.api.TvShowApiService
import com.elhady.movies.core.network.dto.common.YoutubeVideoDetailsDto
import com.elhady.movies.core.network.dto.tvshow.RateRequest
import com.elhady.movies.core.network.dto.tvshow.RatingEpisodeDetailsRequest
import java.util.Random
import javax.inject.Inject
import com.elhady.movies.core.domain.model.tvshow.TvShow as DomainTvShowEntity

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowApiService: TvShowApiService,
    private val accountApiService: AccountApiService,
    private val peopleApiService: PeopleApiService,
    private val tvShowDao: TvShowDao,
    private val airingTodayTvShowDao: AiringTodayTvShowDao,
    private val airingTodayTvShowsPagingSource: AiringTodayTVShowsPagingSource,
    private val topRatedTVShowsPagingSource: TopRatedTVShowsPagingSource,
    private val onTheAirTVShowsPagingSource: OnTheAirTVShowsPagingSource,
    private val popularTVShowsPagingSource: PopularTVShowsPagingSource,
    private val domainTvDetailsMapper: DomainTvDetailsMapper,
    private val domainYoutubeDetailsMapper: DomainYoutubeDetailsMapper,
    private val domainTvDetailsCreditMapper: DomainTvDetailsCreditMapper,
    private val domainTvDetailsReviewMapper: DomainTvDetailsReviewMapper,
    private val domainTvShowMapper: DomainTvShowMapper,
    private val domainTVMapper: DomainTVMapper,
    private val domainTvDetailsSeasonMapper: DomainTvDetailsSeasonMapper,
    private val localTvShowMapper: LocalTvShowMapper,
    private val domainAiringTodayTvShowsMapper: DomainAiringTodayTvShowsMapper,
    private val localAiringTodayTvShowMapper: LocalAiringTodayTvShowMapper,
    private val domainAiringTodayTVMapper: DomainAiringTodayTVMapper,
    private val domainSeasonDetailsMapper: DomainSeasonDetailsMapper,
    private val domainCastMapper: DomainCastMapper,
    private val domainEpisodeDetailsMapper: DomainEpisodeDetailsMapper,
    private val domainRatingEpisodeMapper: DomainRatingEpisodeMapper,
    private val domainStatusMapper: DomainStatusMapper,
    private val domainMyRatedTvShowDetailsMapper: DomainMyRatedTvShowDetailsMapper,
    private val tvShowsByPeopleMapper: DomainTvShowsByPeopleMapper,
    private val ratedTvShowPagingSource: RatedTvShowPagingSource,
    private val random: Random
) : BaseRepository(), TvShowRepository {

    override suspend fun refreshTvShows() {
        try {
            val items = mutableListOf<TvShowEntity>()
            tvShowApiService.getAiringTodayTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            tvShowApiService.getTopRatedTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            tvShowApiService.getPopularTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            tvShowApiService.getOnTheAirTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            tvShowDao.clearAllTvShow()
            tvShowDao.insertTvShow(items)
        } catch (throwable: Throwable) {
            throw ApiThrowable(throwable.message)
        }
    }

    override suspend fun getTvShowsFromDatabase(): List<TvShows> {
        return domainTVMapper.map(tvShowDao.getAllTvShow())
    }

    override suspend fun refreshAiringTodayTvShows() {
        refreshWrapper(
            apiCall = { tvShowApiService.getAiringTodayTVShows(random.nextInt(20) + 1) },
            localMapper = localAiringTodayTvShowMapper::map,
            databaseSaver = airingTodayTvShowDao::insertAiringTodayTvShow,
            clearOldLocalData = airingTodayTvShowDao::clearAllAiringTodayTvShow
        )
    }

    override suspend fun getAiringTodayTVShowsFromDatabase(): List<TvShows> {
        return domainAiringTodayTVMapper.map(airingTodayTvShowDao.getAllAiringTodayTvShow())
    }

    override suspend fun getAiringTodayTVShowsFromRemote(): List<TvShows> {
        val page = random.nextInt(500) + 1
        val airingTodayDtos =
            wrapApiCall { tvShowApiService.getAiringTodayTVShows(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return domainAiringTodayTvShowsMapper.map(airingTodayDtos)
    }

    override suspend fun getAiringTodayTVShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { airingTodayTvShowsPagingSource }
        )
    }

    override suspend fun getTopRatedTVShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedTVShowsPagingSource }
        )
    }

    override suspend fun getPopularTVShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { popularTVShowsPagingSource }
        )
    }

    override suspend fun getOnTheAirTVShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { onTheAirTVShowsPagingSource }
        )
    }

    override suspend fun getSeasonDetails(seriesId: Int, seasonId: Int): SeasonDetails {
        val result = wrapApiCall { tvShowApiService.getSeasonDetails(seriesId, seasonId) }
        return domainSeasonDetailsMapper.map(result)
    }

    override suspend fun getTvDetailsInfo(tvShowID: Int): TvDetailsInfo {
        return domainTvDetailsMapper.map(wrapApiCall { tvShowApiService.getTvDetails(tvShowID) })
    }

    override suspend fun getTvDetailsSeasons(tvShowID: Int): List<Season> {
        return domainTvDetailsSeasonMapper.map(wrapApiCall { tvShowApiService.getTvDetails(tvShowID) })
    }

    override suspend fun getTvDetailsCredit(tvShowID: Int): List<People> {
        return domainTvDetailsCreditMapper.map(wrapApiCall {
            tvShowApiService.getTvDetailsCredit(
                tvShowID
            )
        })
    }

    override suspend fun rateTvShow(rate: Double, tvShowID: Int): Status {
        val newRate = RateRequest(value = rate)
        return domainStatusMapper.map(wrapApiCall { tvShowApiService.rateTvShow(newRate, tvShowID) })
    }

    override suspend fun getRateTvShow(): List<MyRatedTvShow> {
        return domainMyRatedTvShowDetailsMapper.map(
            wrapApiCall { accountApiService.getRatedTv() }.results?.filterNotNull() ?: emptyList()
        )
    }

    override suspend fun getTvShowReviews(tvShowID: Int): List<Review> {
        val call = wrapApiCall { tvShowApiService.getTvShowReviews(tvShowID) }.results?.filterNotNull()
            ?: emptyList()
        return domainTvDetailsReviewMapper.map(call)
    }

    override suspend fun getTvShowRecommendations(tvShowID: Int): List<DomainTvShowEntity> {
        val call =
            wrapApiCall { tvShowApiService.getTvShowRecommendations(tvShowID) }.results?.filterNotNull()
                ?: emptyList()
        return domainTvShowMapper.map(call)
    }

    override suspend fun getTrailerVideoForTvShow(tvShowID: Int): YoutubeVideoDetails {
        val call = wrapApiCall { tvShowApiService.getTrailerVideoForTvShow(tvShowID) }.results?.first()
            ?: YoutubeVideoDetailsDto()
        return domainYoutubeDetailsMapper.map(call)
    }

    override suspend fun getVideoEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): YoutubeVideoDetails {
        val response = wrapApiCall {
            tvShowApiService.getEpisodeVideos(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        }.results?.first() ?: YoutubeVideoDetailsDto()
        return domainYoutubeDetailsMapper.map(response)
    }

    override suspend fun getCastForEpisode(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<People> {
        val dataDto = wrapApiCall { tvShowApiService.getEpisodeCast(id, seasonNumber, episodeNumber) }
        return domainCastMapper.map(dataDto)
    }

    override suspend fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetails {
        return domainEpisodeDetailsMapper.map(wrapApiCall {
            tvShowApiService.getEpisodeDetails(seriesId, seasonNumber, episodeNumber)
        })
    }

    override suspend fun setRatingForEpisode(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatus {
        val rateRequest = RatingEpisodeDetailsRequest(value)
        return domainRatingEpisodeMapper.map(wrapApiCall {
            tvShowApiService.postEpisodeRating(rateRequest, seriesId, seasonNumber, episodeNumber)
        })
    }

    override suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShow> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ratedTvShowPagingSource }
        )
    }

    override suspend fun getTvShowsByPerson(personId: Int): List<DomainTvShowEntity> {
        return tvShowsByPeopleMapper.map(wrapApiCall {
            peopleApiService.getTvShowsByPerson(personId)
        }.cast!!.filterNotNull())

    }
}
