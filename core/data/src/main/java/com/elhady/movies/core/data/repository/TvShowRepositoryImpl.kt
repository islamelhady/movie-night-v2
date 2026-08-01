package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.common.ApiThrowable
import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.common.StatusDtoMapper
import com.elhady.movies.core.data.mapper.common.YoutubeDetailsDtoMapper
import com.elhady.movies.core.data.mapper.episode.CastDtoMapper
import com.elhady.movies.core.data.mapper.episode.EpisodeDetailsDtoMapper
import com.elhady.movies.core.data.mapper.episode.RatingEpisodeDtoMapper
import com.elhady.movies.core.data.mapper.people.TvShowCastDtoMapper
import com.elhady.movies.core.data.mapper.season.SeasonDetailsDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.AiringTodayTvEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.AiringTodayTvShowDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.MyRatedTvShowDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.TvEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.TvDetailsCreditDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.TvDetailsDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.TvDetailsReviewDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.TvDetailsSeasonDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.TvShowEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.AiringTodayTvShowDtoToEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.TvShowDtoToEntityMapper
import com.elhady.movies.core.data.paging.tvshow.AiringTodayTvShowPagingSource
import com.elhady.movies.core.data.paging.tvshow.OnTheAirTvShowPagingSource
import com.elhady.movies.core.data.paging.tvshow.PopularTvShowPagingSource
import com.elhady.movies.core.data.paging.tvshow.RatedTvShowPagingSource
import com.elhady.movies.core.data.paging.tvshow.TopRatedTvShowPagingSource
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
    private val airingTodayTvShowPagingSource: AiringTodayTvShowPagingSource,
    private val topRatedTvShowPagingSource: TopRatedTvShowPagingSource,
    private val onTheAirTvShowPagingSource: OnTheAirTvShowPagingSource,
    private val popularTvShowPagingSource: PopularTvShowPagingSource,
    private val tvDetailsDtoMapper: TvDetailsDtoMapper,
    private val domainYoutubeDetailsMapper: YoutubeDetailsDtoMapper,
    private val tvDetailsCreditDtoMapper: TvDetailsCreditDtoMapper,
    private val tvDetailsReviewDtoMapper: TvDetailsReviewDtoMapper,
    private val tvShowEntityMapper: TvShowEntityMapper,
    private val tvEntityMapper: TvEntityMapper,
    private val tvDetailsSeasonDtoMapper: TvDetailsSeasonDtoMapper,
    private val tvShowDtoToEntityMapper: TvShowDtoToEntityMapper,
    private val airingTodayTvShowDtoMapper: AiringTodayTvShowDtoMapper,
    private val airingTodayTvShowDtoToEntityMapper: AiringTodayTvShowDtoToEntityMapper,
    private val airingTodayTvEntityMapper: AiringTodayTvEntityMapper,
    private val seasonDetailsDtoMapper: SeasonDetailsDtoMapper,
    private val domainCastMapper: CastDtoMapper,
    private val domainEpisodeDetailsMapper: EpisodeDetailsDtoMapper,
    private val domainRatingEpisodeMapper: RatingEpisodeDtoMapper,
    private val domainStatusMapper: StatusDtoMapper,
    private val myRatedTvShowDtoMapper: MyRatedTvShowDtoMapper,
    private val tvShowCastDtoMapper: TvShowCastDtoMapper,
    private val ratedTvShowPagingSource: RatedTvShowPagingSource,
    private val random: Random
) : BaseRepository(), TvShowRepository {

    override suspend fun refreshTvShows() {
        try {
            val items = mutableListOf<TvShowEntity>()
            tvShowApiService.getAiringTodayTvShows().body()?.results?.firstOrNull()
                ?.let { items.add(tvShowDtoToEntityMapper.map(it)) }
            tvShowApiService.getTopRatedTvShows().body()?.results?.firstOrNull()
                ?.let { items.add(tvShowDtoToEntityMapper.map(it)) }
            tvShowApiService.getPopularTvShows().body()?.results?.firstOrNull()
                ?.let { items.add(tvShowDtoToEntityMapper.map(it)) }
            tvShowApiService.getOnTheAirTvShows().body()?.results?.firstOrNull()
                ?.let { items.add(tvShowDtoToEntityMapper.map(it)) }
            tvShowDao.clearAllTvShow()
            tvShowDao.insertTvShow(items)
        } catch (throwable: Throwable) {
            throw ApiThrowable(throwable.message)
        }
    }

    override suspend fun getTvShowsFromDatabase(): List<TvShows> {
        return tvEntityMapper.map(tvShowDao.getAllTvShow())
    }

    override suspend fun refreshAiringTodayTvShows() {
        refreshWrapper(
            apiCall = { tvShowApiService.getAiringTodayTvShows(random.nextInt(20) + 1) },
            localMapper = airingTodayTvShowDtoToEntityMapper::map,
            databaseSaver = airingTodayTvShowDao::insertAiringTodayTvShow,
            clearOldLocalData = airingTodayTvShowDao::clearAllAiringTodayTvShow
        )
    }

    override suspend fun getAiringTodayTvShowsFromDatabase(): List<TvShows> {
        return airingTodayTvEntityMapper.map(airingTodayTvShowDao.getAllAiringTodayTvShow())
    }

    override suspend fun getAiringTodayTvShowsFromRemote(): List<TvShows> {
        val page = random.nextInt(500) + 1
        val airingTodayDtos =
            wrapApiCall { tvShowApiService.getAiringTodayTvShows(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return airingTodayTvShowDtoMapper.map(airingTodayDtos)
    }

    override suspend fun getAiringTodayTvShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { airingTodayTvShowPagingSource }
        )
    }

    override suspend fun getTopRatedTvShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedTvShowPagingSource }
        )
    }

    override suspend fun getPopularTvShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { popularTvShowPagingSource }
        )
    }

    override suspend fun getOnTheAirTvShowsPager(): Pager<Int, TvShows> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { onTheAirTvShowPagingSource }
        )
    }

    override suspend fun getSeasonDetails(seriesId: Int, seasonId: Int): SeasonDetails {
        val result = wrapApiCall { tvShowApiService.getSeasonDetails(seriesId, seasonId) }
        return seasonDetailsDtoMapper.map(result)
    }

    override suspend fun getTvDetailsInfo(tvShowID: Int): TvDetailsInfo {
        return tvDetailsDtoMapper.map(wrapApiCall { tvShowApiService.getTvDetails(tvShowID) })
    }

    override suspend fun getTvDetailsSeasons(tvShowID: Int): List<Season> {
        return tvDetailsSeasonDtoMapper.map(wrapApiCall { tvShowApiService.getTvDetails(tvShowID) })
    }

    override suspend fun getTvDetailsCredit(tvShowID: Int): List<People> {
        return tvDetailsCreditDtoMapper.map(wrapApiCall {
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
        return myRatedTvShowDtoMapper.map(
            wrapApiCall { accountApiService.getRatedTv() }.results?.filterNotNull() ?: emptyList()
        )
    }

    override suspend fun getTvShowReviews(tvShowID: Int): List<Review> {
        val call = wrapApiCall { tvShowApiService.getTvShowReviews(tvShowID) }.results?.filterNotNull()
            ?: emptyList()
        return tvDetailsReviewDtoMapper.map(call)
    }

    override suspend fun getTvShowRecommendations(tvShowID: Int): List<DomainTvShowEntity> {
        val call =
            wrapApiCall { tvShowApiService.getTvShowRecommendations(tvShowID) }.results?.filterNotNull()
                ?: emptyList()
        return tvShowEntityMapper.map(call)
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
        return tvShowCastDtoMapper.map(wrapApiCall {
            peopleApiService.getTvShowsByPerson(personId)
        }.cast!!.filterNotNull())

    }
}
