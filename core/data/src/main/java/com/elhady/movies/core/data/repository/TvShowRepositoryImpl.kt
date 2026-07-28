package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.common.ApiThrowable
import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.paging.tvshow.RatedTvShowPagingSource
import com.elhady.movies.core.data.paging.tvshow.AiringTodayTVShowsPagingSource
import com.elhady.movies.core.data.paging.tvshow.OnTheAirTVShowsPagingSource
import com.elhady.movies.core.data.paging.tvshow.PopularTVShowsPagingSource
import com.elhady.movies.core.data.paging.tvshow.TopRatedTVShowsPagingSource
import com.elhady.movies.core.data.mapper.tvshow.LocalAiringTodayTvShowMapper
import com.elhady.movies.core.data.mapper.tvshow.LocalTvShowMapper
import com.elhady.movies.core.data.mapper.season.DomainSeasonDetailsMapper
import com.elhady.movies.core.data.mapper.common.DomainStatusMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsCreditMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsReviewMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvDetailsSeasonMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTvShowMapper
import com.elhady.movies.core.data.mapper.common.DomainYoutubeDetailsMapper
import com.elhady.movies.core.data.mapper.episode.DomainCastMapper
import com.elhady.movies.core.data.mapper.episode.DomainEpisodeDetailsMapper
import com.elhady.movies.core.data.mapper.episode.DomainRatingEpisodeMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainAiringTodayTVMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainMyRatedTvShowDetailsMapper
import com.elhady.movies.core.data.mapper.people.DomainTvShowsByPeopleMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainTVMapper
import com.elhady.movies.core.database.TvShowDao
import com.elhady.movies.core.database.dto.tvshow.TvShowsLocalDto
import com.elhady.movies.core.domain.model.tvshow.EpisodeDetailsEntity
import com.elhady.movies.core.domain.model.people.PeopleEntity
import com.elhady.movies.core.domain.model.tvshow.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.domain.model.common.ReviewEntity
import com.elhady.movies.core.domain.model.tvshow.SeasonEntity
import com.elhady.movies.core.domain.model.common.StatusEntity
import com.elhady.movies.core.domain.model.tvshow.TVShowsEntity
import com.elhady.movies.core.domain.model.tvshow.TvShowEntity
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetailsEntity
import com.elhady.movies.core.domain.model.account.MyRatedTvShowEntity
import com.elhady.movies.core.domain.model.tvshow.SeasonDetailsEntity
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfoEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import com.elhady.movies.core.network.model.request.RateRequest
import com.elhady.movies.core.network.model.request.RatingEpisodeDetailsRequest
import com.elhady.movies.core.network.model.response.dto.YoutubeVideoDetailsRemoteDto
import com.elhady.movies.core.network.service.MovieService
import java.util.Random
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val tvShowDao: TvShowDao,
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
            val items = mutableListOf<TvShowsLocalDto>()
            movieService.getAiringTodayTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            movieService.getTopRatedTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            movieService.getPopularTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            movieService.getOnTheAirTVShows().body()?.results?.firstOrNull()
                ?.let { items.add(localTvShowMapper.map(it)) }
            tvShowDao.clearAllTvShow()
            tvShowDao.insertTvShow(items)
        } catch (throwable: Throwable) {
            throw ApiThrowable(throwable.message)
        }
    }

    override suspend fun getTvShowsFromDatabase(): List<TVShowsEntity> {
        return domainTVMapper.map(tvShowDao.getAllTvShow())
    }

    override suspend fun refreshAiringTodayTvShows() {
        refreshWrapper(
            apiCall = { movieService.getAiringTodayTVShows(random.nextInt(20) + 1) },
            localMapper = localAiringTodayTvShowMapper::map,
            databaseSaver = tvShowDao::insertAiringTodayTvShow,
            clearOldLocalData = tvShowDao::clearAllAiringTodayTvShow
        )
    }

    override suspend fun getAiringTodayTvShowsFromDatabase(): List<TVShowsEntity> {
        return domainAiringTodayTVMapper.map(tvShowDao.getAllAiringTodayTvShow())
    }

    override suspend fun getAiringTodayTVShowsFromRemote(): List<TVShowsEntity> {
        val page = random.nextInt(500) + 1
        val airingTodayRemoteDTOs =
            wrapApiCall { movieService.getAiringTodayTVShows(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return domainAiringTodayTvShowsMapper.map(airingTodayRemoteDTOs)
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
            pagingSourceFactory = { topRatedTVShowsPagingSource }
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

    override suspend fun getSeasonDetails(seriesId: Int, seasonId: Int): SeasonDetailsEntity {
        val result = wrapApiCall { movieService.getSeasonDetails(seriesId, seasonId) }
        return domainSeasonDetailsMapper.map(result)
    }

    override suspend fun getTvDetailsInfo(tvShowID: Int): TvDetailsInfoEntity {
        return domainTvDetailsMapper.map(wrapApiCall { movieService.getTvDetails(tvShowID) })
    }

    override suspend fun getTvDetailsSeasons(tvShowID: Int): List<SeasonEntity> {
        return domainTvDetailsSeasonMapper.map(wrapApiCall { movieService.getTvDetails(tvShowID) })
    }

    override suspend fun getTvDetailsCredit(tvShowID: Int): List<PeopleEntity> {
        return domainTvDetailsCreditMapper.map(wrapApiCall {
            movieService.getTvDetailsCredit(
                tvShowID
            )
        })
    }

    override suspend fun rateTvShow(rate: Double, tvShowID: Int): StatusEntity {
        val newRate = RateRequest(value = rate)
        return domainStatusMapper.map(wrapApiCall { movieService.rateTvShow(newRate, tvShowID) })
    }

    override suspend fun getRateTvShow(): List<MyRatedTvShowEntity> {
        return domainMyRatedTvShowDetailsMapper.map(
            wrapApiCall { movieService.getRatedTv() }.results?.filterNotNull() ?: emptyList()
        )
    }

    override suspend fun getTvShowReviews(tvShowID: Int): List<ReviewEntity> {
        val call = wrapApiCall { movieService.getTvShowReviews(tvShowID) }.results?.filterNotNull()
            ?: emptyList()
        return domainTvDetailsReviewMapper.map(call)
    }

    override suspend fun getTvShowRecommendations(tvShowID: Int): List<TvShowEntity> {
        val call =
            wrapApiCall { movieService.getTvShowRecommendations(tvShowID) }.results?.filterNotNull()
                ?: emptyList()
        return domainTvShowMapper.map(call)
    }

    override suspend fun getTrailerVideoForTvShow(tvShowID: Int): YoutubeVideoDetailsEntity {
        val call = wrapApiCall { movieService.getTrailerVideoForTvShow(tvShowID) }.results?.first()
            ?: YoutubeVideoDetailsRemoteDto()
        return domainYoutubeDetailsMapper.map(call)
    }

    override suspend fun getVideoEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): YoutubeVideoDetailsEntity {
        val response = wrapApiCall {
            movieService.getEpisodeVideos(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        }.results?.first() ?: YoutubeVideoDetailsRemoteDto()
        return domainYoutubeDetailsMapper.map(response)
    }

    override suspend fun getCastForEpisode(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<PeopleEntity> {
        val dataDto = wrapApiCall { movieService.getEpisodeCast(id, seasonNumber, episodeNumber) }
        return domainCastMapper.map(dataDto)
    }

    override suspend fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetailsEntity {
        return domainEpisodeDetailsMapper.map(wrapApiCall {
            movieService.getEpisodeDetails(seriesId, seasonNumber, episodeNumber)
        })
    }

    override suspend fun setRatingForEpisode(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatusEntity {
        val rateRequest = RatingEpisodeDetailsRequest(value)
        return domainRatingEpisodeMapper.map(wrapApiCall {
            movieService.postEpisodeRating(rateRequest, seriesId, seasonNumber, episodeNumber)
        })
    }

    override suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShowEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ratedTvShowPagingSource }
        )
    }

    override suspend fun getTvShowsByPerson(personId: Int): List<TvShowEntity> {
        return tvShowsByPeopleMapper.map(wrapApiCall {
            movieService.getTvShowsByPerson(personId)
        }.cast!!.filterNotNull())

    }
}
