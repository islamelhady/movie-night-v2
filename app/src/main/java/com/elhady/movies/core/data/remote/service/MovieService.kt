package com.elhady.movies.core.data.remote.service

import com.elhady.movies.core.data.remote.request.AddMediaToListRequest
import com.elhady.movies.core.data.remote.request.CreateUserListRequest
import com.elhady.movies.core.data.remote.request.DeleteMovieRequest
import com.elhady.movies.core.data.remote.request.FavoriteRequest
import com.elhady.movies.core.data.remote.request.ListRequest
import com.elhady.movies.core.data.remote.request.LoginRequest
import com.elhady.movies.core.data.remote.request.RateRequest
import com.elhady.movies.core.data.remote.request.RatingEpisodeDetailsRequest
import com.elhady.movies.core.data.remote.request.RatingRequest
import com.elhady.movies.core.data.remote.request.WatchlistRequest
import com.elhady.movies.core.data.remote.response.DataWrapperResponse
import com.elhady.movies.core.data.remote.response.GenresWrapperResponse
import com.elhady.movies.core.data.remote.response.ListDetailsWrapperResponse
import com.elhady.movies.core.data.remote.response.ListResponse
import com.elhady.movies.core.data.remote.response.auth.RequestTokenResponse
import com.elhady.movies.core.data.remote.response.auth.SessionResponse
import com.elhady.movies.core.data.remote.response.dto.GenreMovieRemoteDto
import com.elhady.movies.core.data.remote.response.dto.GenreTVRemoteDto
import com.elhady.movies.core.data.remote.response.dto.ListRemoteDto
import com.elhady.movies.core.data.remote.response.dto.MovieRemoteDto
import com.elhady.movies.core.data.remote.response.dto.MoviesByPeopleResponse
import com.elhady.movies.core.data.remote.response.dto.PeopleDetailsResponse
import com.elhady.movies.core.data.remote.response.dto.PeopleRemoteDto
import com.elhady.movies.core.data.remote.response.dto.StatusResponse
import com.elhady.movies.core.data.remote.response.dto.TVShowsRemoteDto
import com.elhady.movies.core.data.remote.response.dto.TvDetailsCreditRemoteDto
import com.elhady.movies.core.data.remote.response.dto.TvDetailsRemoteDto
import com.elhady.movies.core.data.remote.response.dto.TvRemoteDto
import com.elhady.movies.core.data.remote.response.dto.TvReviewRemoteDto
import com.elhady.movies.core.data.remote.response.dto.TvShowsByPeopleResponse
import com.elhady.movies.core.data.remote.response.dto.UserListRemoteDto
import com.elhady.movies.core.data.remote.response.dto.YoutubeVideoDetailsRemoteDto
import com.elhady.movies.core.data.remote.response.dto.episode_details.EpisodeDetailsCastRemoteDto
import com.elhady.movies.core.data.remote.response.dto.episode_details.EpisodeDetailsRemoteDto
import com.elhady.movies.core.data.remote.response.dto.episode_details.RatingEpisodeDetailsRemoteDto
import com.elhady.movies.core.data.remote.response.dto.myrated.MyRatedMovieDto
import com.elhady.movies.core.data.remote.response.dto.myrated.MyRatedTvShowDto
import com.elhady.movies.core.data.remote.response.dto.profile.ProfileRemoteDto
import com.elhady.movies.core.data.remote.response.dto.season_details.SeasonDetailsDto
import com.elhady.movies.core.data.remote.response.moviedetails.MovieDetailsRemoteDto
import com.elhady.movies.core.data.remote.response.moviedetails.ReviewsRemoteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /// region auth
    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSession(@Field("request_token") requestToken: String): Response<SessionResponse>

    @GET("authentication/token/new")
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @POST("authentication/token/validate_with_login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<RequestTokenResponse>
    /// endregion

    /// region movie
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Query("page") page: Int = 1,
        @Path("movie_id") movieId: Int
    ): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/latest")
    suspend fun getLatestMovie(): Response<MovieRemoteDto>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MovieRemoteDto>>

    ///endregion

    /// region trailer
    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerVideoForMovie(
        @Path("movie_id") tvShowId: Int
    ): Response<DataWrapperResponse<YoutubeVideoDetailsRemoteDto>>

    @GET("tv/{tv_id}/videos")
    suspend fun getTrailerVideoForTvShow(
        @Path("tv_id") tvShowId: Int
    ): Response<DataWrapperResponse<YoutubeVideoDetailsRemoteDto>>

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/videos")
    suspend fun getEpisodeVideos(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<DataWrapperResponse<YoutubeVideoDetailsRemoteDto>>

    ///endregion

    /// region tv

    @GET("tv/airing_today")
    suspend fun getAiringTodayTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("tv/popular")
    suspend fun getPopularTVShows(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>
    /// endregion

    /// region search
    @GET("search/movie")
    suspend fun searchForMovies(
        @Query("query") query: String,
        @Query("year") year: Int? = null,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("region") region: String? = null,
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("search/tv")
    suspend fun searchForTv(
        @Query("query") query: String,
        @Query("year") year: Int? = null,
        @Query("first_air_date_year") firstAirDateYear: String? = null,
        @Query("region") region: String? = null,
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<TvRemoteDto>>

    @GET("search/person")
    suspend fun searchForPeople(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<PeopleRemoteDto>>

    /// endregion

    /// region popular people
    @GET("person/popular")
    suspend fun getPopularPeople(@Query("page") page: Int = 1): Response<DataWrapperResponse<PeopleRemoteDto>>
    /// endregion

    /// region genres
    @GET("genre/movie/list")
    suspend fun getListOfGenresForMovies(
        @Query("language") language: String = "en"
    ): Response<GenresWrapperResponse<GenreMovieRemoteDto>>

    @GET("genre/tv/list")
    suspend fun getListOfGenresForTvs(
        @Query("language") language: String = "en"
    ): Response<GenresWrapperResponse<GenreTVRemoteDto>>
    ///endregion

    /// region account
    @GET("account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String = " "
    ): Response<ProfileRemoteDto>
    ///endregion

    /// region movie details
    @GET("movie/{movieId}?&append_to_response=videos,credits,recommendations,reviews")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): Response<MovieDetailsRemoteDto>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movieId}/rating")
    suspend fun setMovieRate(
        @Body ratingRequest: RatingRequest,
        @Path("movieId") movieId: Int
    ): Response<StatusResponse>
    /// endregion

    /// region season details
    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int
    ): Response<SeasonDetailsDto>
    ///endregion

    /// region tv details
    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvShowId: Int
    ): Response<TvDetailsRemoteDto>

    @GET("tv/{tv_id}/aggregate_credits")
    suspend fun getTvDetailsCredit(
        @Path("tv_id") tvShowId: Int
    ): Response<TvDetailsCreditRemoteDto>

    @POST("tv/{tv_id}/rating?")
    suspend fun rateTvShow(
        @Body rateRequest: RateRequest, @Path("tv_id") tvShowId: Int,
    ): Response<StatusResponse>

    @GET("tv/{tv_id}/reviews")
    suspend fun getTvShowReviews(
        @Path("tv_id") tvShowId: Int
    ): Response<DataWrapperResponse<TvReviewRemoteDto>>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getTvShowRecommendations(
        @Path("tv_id") tvShowId: Int
    ): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowYoutubeVideoDetails(
        @Path("tv_id") tvShowId: Int
    ): Response<DataWrapperResponse<YoutubeVideoDetailsRemoteDto>>
    /// endregion

    //region my list
    @GET("account/account_id/lists")
    suspend fun getUserLists(): Response<DataWrapperResponse<UserListRemoteDto>>

    @POST("list/{list_id}/add_item")
    suspend fun postUserMedia(
        @Path("list_id") listId: Int,
        @Body mediaId: AddMediaToListRequest
    ): Response<StatusResponse>

    @POST("list")
    suspend fun createUserList(@Body name: CreateUserListRequest): Response<StatusResponse>


    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTv(): Response<DataWrapperResponse<TvRemoteDto>>



    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlist(): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("account/{account_id}/watchlist/tv")
    suspend fun getWatchlistTv(): Response<DataWrapperResponse<TvRemoteDto>>


    @POST("account/{account_id}/watchlist")
    suspend fun addWatchlist(
        @Body watchlistRequest: WatchlistRequest,
    ): Response<StatusResponse>


    @POST("list")
    suspend fun addList(@Body listRequest: ListRequest): Response<ListResponse>

    @DELETE("list/{list_id}")
    suspend fun deleteList(@Path("list_id") listId: Int): Response<StatusResponse>


    @GET("account/{account_id}/lists")
    suspend fun getLists(): Response<DataWrapperResponse<ListRemoteDto>>


    @GET("list/{list_id}")
    suspend fun getDetailsList(@Path("list_id") listId: Int)
            : Response<ListDetailsWrapperResponse<MovieRemoteDto>>

    @POST("list/{list_id}/remove_item")
    suspend fun deleteMovieDetailsList(
        @Path("list_id") listId: Int,
        @Body movieRequest: DeleteMovieRequest,
    ): Response<StatusResponse>


    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1
    ): Response<ReviewsRemoteDto>

    @POST("account/account_id/favorite")
    suspend fun addFavorite(@Body markAsFavorite: FavoriteRequest): Response<StatusResponse>
    //endregion

    /// region episode
    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisodeDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<EpisodeDetailsRemoteDto>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("tv/{series_id}/season/{season_number}/episode/{episode_number}/rating")
    suspend fun postEpisodeRating(
        @Body rate: RatingEpisodeDetailsRequest,
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<RatingEpisodeDetailsRemoteDto>

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/credits")
    suspend fun getEpisodeCast(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Response<EpisodeDetailsCastRemoteDto>

///endregion

    /// region trailer

    /// endregion

    /// region My rating
    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<MyRatedMovieDto>>

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTv(
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MyRatedTvShowDto>>

    ///endregion


    /// region people details

    @GET("person/{person_id}")
    suspend fun getPerson(@Path("person_id") personId: Int): Response<PeopleDetailsResponse>

    @GET("person/{person_id}/movie_credits")
    suspend fun getMoviesByPerson(@Path("person_id") personId: Int): Response<MoviesByPeopleResponse>

    @GET("person/{person_id}/tv_credits")
    suspend fun getTvShowsByPerson(@Path("person_id") personId: Int): Response<TvShowsByPeopleResponse>

    ///endregion
}
