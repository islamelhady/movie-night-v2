package com.elhady.movies.core.domain.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.entities.EpisodeDetailsEntity
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.entities.PeopleDetailsEntity
import com.elhady.movies.core.domain.entities.PeopleEntity
import com.elhady.movies.core.domain.entities.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.domain.entities.SeasonEntity
import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.entities.tvdetails.TvDetailsInfoEntity
import com.elhady.movies.core.domain.entities.TvEntity
import com.elhady.movies.core.domain.entities.TvShowEntity
import com.elhady.movies.core.domain.entities.UserListEntity
import com.elhady.movies.core.domain.entities.YoutubeVideoDetailsEntity
import com.elhady.movies.core.domain.entities.moviedetails.MovieDetailsEntity
import com.elhady.movies.core.domain.entities.ReviewEntity
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.entities.moviedetails.ReviewResponseEntity
import com.elhady.movies.core.domain.entities.mylist.ListCreatedEntity
import com.elhady.movies.core.domain.entities.myrated.MyRatedMovieEntity
import com.elhady.movies.core.domain.entities.myrated.MyRatedTvShowEntity
import com.elhady.movies.core.domain.entities.seasondetails.SeasonDetailsEntity

interface MovieRepository {


    //showMore
    suspend fun getPopularMoviesPaging(): Pager<Int, MovieEntity>
    suspend fun getTopRateMoviesPaging(): Pager<Int, MovieEntity>
    suspend fun getTrendingMoviesPaging(): Pager<Int, MovieEntity>
    suspend fun getPopularMoviesFromDatabase(): List<MovieEntity>
    suspend fun getPopularMoviesFromRemote(): List<MovieEntity>
    suspend fun refreshPopularMovies()

    suspend fun getNowPlayingMovies(): List<MovieEntity>
    suspend fun refreshNowPlayingMovies()

    suspend fun getTopRatedMovies(): List<MovieEntity>
    suspend fun refreshTopRatedMovies()

    suspend fun getUpcomingMoviesFromDatabase(): List<MovieEntity>
    suspend fun refreshUpcomingMovies()

    suspend fun getPopularPeopleFromDatabase(): List<PeopleEntity>
    suspend fun refreshTrendingMovies()
    suspend fun getPopularPeopleFromRemote(): List<PeopleEntity>

    suspend fun getTrendingMovies(): List<MovieEntity>
    suspend fun refreshPopularPeople()

    suspend fun getSearchHistory(keyword: String): List<String>
    suspend fun searchHistory(): List<String>
    suspend fun insertSearchHistory(keyword: String)
    suspend fun clearAllSearchHistory()
    suspend fun deleteSearchHistory(keyword: String)

    suspend fun searchForMovies(keyword: String): List<MovieEntity>

    suspend fun searchForTv(keyword: String): List<TvEntity>

    suspend fun searchForPeople(keyword: String): List<PeopleEntity>

    suspend fun getGenresMovies(): List<GenreEntity>
    suspend fun refreshGenres()
    suspend fun getMoviesDetails(movieId: Int): MovieDetailsEntity
    suspend fun setMovieRate(movieId: Int, rate: Float): StatusEntity
    suspend fun getMovieRate(): List<MyRatedMovieEntity>
    suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponseEntity

    suspend fun getGenresTvs(): List<GenreEntity>
    suspend fun refreshGenresTv()
    suspend fun getLastRefreshTime(): Long?
    suspend fun setLastRefreshTime(time: Long)
    suspend fun refreshAll()

    // region tv shows
    suspend fun refreshTvShows()
    suspend fun getTvShowsFromDatabase(): List<TVShowsEntity>
    suspend fun refreshAiringTodayTvShows()
    suspend fun getAiringTodayTvShowsFromDatabase(): List<TVShowsEntity>
    suspend fun getAiringTodayTVShowsFromRemote(): List<TVShowsEntity>
    suspend fun getAiringTodayTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getTopRatedTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getPopularTVShowsPager(): Pager<Int, TVShowsEntity>
    suspend fun getOnTheAirTVShowsPager(): Pager<Int, TVShowsEntity>
    // endregion

    /// region season details
    suspend fun getSeasonDetails(seriesId: Int, seasonId: Int): SeasonDetailsEntity

    /// endregion
    suspend fun getTvDetailsInfo(tvShowID: Int): TvDetailsInfoEntity
    suspend fun getTvDetailsSeasons(tvShowID: Int): List<SeasonEntity>
    suspend fun getTvDetailsCredit(tvShowID: Int): List<PeopleEntity>
    suspend fun rateTvShow(rate: Double, tvShowID: Int): StatusEntity
    suspend fun getRateTvShow(): List<MyRatedTvShowEntity>

    suspend fun getTvShowReviews(tvShowID: Int): List<ReviewEntity>
    suspend fun getTvShowRecommendations(tvShowID: Int): List<TvShowEntity>
    suspend fun getTrailerVideoForTvShow(tvShowID: Int): YoutubeVideoDetailsEntity
    suspend fun getTrailerVideoForMovie(movieID: Int): YoutubeVideoDetailsEntity

    suspend fun getVideoEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): YoutubeVideoDetailsEntity

    suspend fun getUserLists(): List<UserListEntity>
    suspend fun postUserLists(listId: Int, mediaId: Int): StatusEntity
    suspend fun createUserList(listName: String): StatusEntity


    suspend fun getFavoriteMovies(): List<MovieEntity>
    suspend fun getFavoriteTv(): List<MovieEntity>
    suspend fun getWatchlistMovies(): List<MovieEntity>
    suspend fun getWatchlistTv(): List<MovieEntity>

    suspend fun addList(name: String): Boolean

    suspend fun getDetailsList(listId: Int): List<MovieEntity>


    suspend fun deleteMovieDetailsList(listId: Int, mediaId: Int): StatusEntity
    suspend fun deleteList(listId: Int): StatusEntity


    suspend fun getListCreated(): List<ListCreatedEntity>

    suspend fun addWatchlist(mediaId: Int, mediaType: String, isWatchList: Boolean): StatusEntity
    suspend fun addFavouriteList(
        mediaId: Int,
        mediaType: String,
        isFavourite: Boolean
    ): StatusEntity

    suspend fun getCastForEpisode(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<PeopleEntity>

    suspend fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetailsEntity

    suspend fun setRatingForEpisode(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatusEntity

    fun isLoginOrNot(): Boolean


    suspend fun getRatedMovies(): Pager<Int, MyRatedMovieEntity>

    suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShowEntity>

    suspend fun getPersonDetails(personId: Int): PeopleDetailsEntity
    suspend fun getMoviesByPerson(personId: Int): List<MovieEntity>
    suspend fun getTvShowsByPerson(personId: Int): List<TvShowEntity>
}