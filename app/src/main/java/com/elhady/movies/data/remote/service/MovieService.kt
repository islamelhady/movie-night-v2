package com.elhady.movies.data.remote.service

import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.AddListResponse
import com.elhady.movies.data.remote.response.AddMovieDto
import com.elhady.movies.data.remote.response.CreatedListDto
import com.elhady.movies.data.remote.response.CreditsDto
import com.elhady.movies.data.remote.response.FavListDto
import com.elhady.movies.data.remote.response.RatedMovieDto
import com.elhady.movies.data.remote.response.RatedSeriesDto
import com.elhady.movies.data.remote.response.RatingDto
import com.elhady.movies.data.remote.response.StatusResponseDto
import com.elhady.movies.data.remote.response.TrendingDto
import com.elhady.movies.data.remote.response.account.AccountDto
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.actor.PersonDto
import com.elhady.movies.data.remote.response.actor.MovieCreditsDto
import com.elhady.movies.data.remote.response.genre.GenreResponse
import com.elhady.movies.data.remote.response.login.RequestTokenResponse
import com.elhady.movies.data.remote.response.login.SessionResponse
import com.elhady.movies.data.remote.response.movie.MovieDetailsDto
import com.elhady.movies.data.remote.response.review.ReviewDto
import com.elhady.movies.data.remote.response.series.SeasonDto
import com.elhady.movies.data.remote.response.series.SeriesDetailsDto
import com.elhady.movies.data.remote.response.series.SeriesDto
import com.elhady.movies.data.remote.response.video.VideoDto
import com.elhady.movies.domain.enums.TrendingTimeWindow
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     *  MOVIE LISTS
     * * Popular
     * * Upcoming
     * * Top Rated
     * * Now Playing
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>


    /**
     *  Trending
     * * All
     * * Movies
     * * People
     */
    @GET("trending/all/{time_window}")
    suspend fun getTrending(@Path("time_window") timeWindow: String = TrendingTimeWindow.DAY.value): Response<BaseResponse<TrendingDto>>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovie(@Path("time_window") timeWindow: String = TrendingTimeWindow.WEEK.value, @Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("trending/person/{time_window}")
    suspend fun getTrendingPerson(
        @Path("time_window") timeWindow: String = TrendingTimeWindow.DAY.value,
        @Query("page") page: Int = 1
    ): Response<BaseResponse<PersonDto>>


    /**
     *  PEOPLE LISTS
     */
    @GET("person/popular")
    suspend fun getPopularPerson(@Query("page") page: Int = 1): Response<BaseResponse<PersonDto>>

    @GET("person/{person_id}")
    suspend fun getPersonsDetails(@Path("person_id") actorID: Int): Response<PersonDto>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovies(@Path("person_id") actorID: Int): Response<MovieCreditsDto>

    /**
     *  GENRES
     * * Movies
     * * Series
     */
    @GET("genre/movie/list")
    suspend fun getGenreMovies(): Response<GenreResponse>

    @GET("genre/tv/list")
    suspend fun getGenreSeries(): Response<GenreResponse>

    /**
     * Movies
     * * Details
     * * Credits (Cast)
     * * Similar
     */
    @GET("movie/{movie_id}")
    suspend fun getDetailsMovies(@Path("movie_id") movieId: Int): Response<MovieDetailsDto>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(@Path("movie_id") movieId: Int): Response<CreditsDto>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(@Path("movie_id") movieId: Int): Response<BaseResponse<MovieDto>>

    /**
     * TV SERIES
     * * Details
     * * Cast
     * * Similar
     * * Seasons
     */
    @GET("tv/{series_id}")
    suspend fun getSeriesDetails(@Path("series_id") seriesId: Int): Response<SeriesDetailsDto>

    @GET("tv/{series_id}/credits")
    suspend fun getSeriesCast(@Path("series_id") seriesId: Int): Response<CreditsDto>

    @GET("tv/{series_id}/similar")
    suspend fun getSimilarSeries(@Path("series_id") seriesId: Int): Response<BaseResponse<SeriesDto>>

    /**
     *  TV SEASONS
     * * Details
     */

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getSeasonDetails(@Path("series_id") seriesId: Int, @Path("season_number") seasonNumber: Int): Response<SeasonDto>

    /**
     *  Review
     * * movie
     * * series
     */
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(@Path("movie_id") movieId: Int): Response<BaseResponse<ReviewDto>>

    @GET("tv/{series_id}/reviews")
    suspend fun getSeriesReview(@Path("series_id") seriesId: Int): Response<BaseResponse<ReviewDto>>


    /**
     *   TV SERIES LISTS
     * * Airing Today
     * * On The Air
     * * Popular
     * * Top Rated
     */
    @GET("tv/airing_today")
    suspend fun getAiringTodayTV(): Response<BaseResponse<SeriesDto>>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTV(@Query("page") page: Int = 1): Response<BaseResponse<SeriesDto>>

    @GET("tv/popular")
    suspend fun getPopularTV(@Query("page") page: Int =1): Response<BaseResponse<SeriesDto>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(@Query("page") page: Int = 1): Response<BaseResponse<SeriesDto>>

    /**
     *   DISCOVER
     * * All Movies
     * * Movies by Genre
     * * All Series
     * * Series TV by Genre
     */
    @GET("discover/movie")
    suspend fun getAllMovies(@Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>
    @GET("discover/movie")
    suspend fun getMoviesListByGenre(@Query("with_genres") genreID: Int, @Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("discover/tv")
    suspend fun getAllSeries(@Query("page") page: Int = 1): Response<BaseResponse<SeriesDto>>
    @GET("discover/tv")
    suspend fun getSeriesByGenre(@Query("with_genres") genreID: Int ,@Query("page") page: Int = 1): Response<BaseResponse<SeriesDto>>


    /**
     * Search
     * * Movies
     * * Series
     * * Actors
     */
    @GET("search/movie")
    suspend fun searchForMovies(@Query("query") query: String, @Query("page") page: Int = 1): Response<BaseResponse<MovieDto>>

    @GET("search/tv")
    suspend fun searchForSeries(@Query("query") query: String, @Query("page") page: Int = 1): Response<BaseResponse<SeriesDto>>

    @GET("search/person")
    suspend fun searchForActors(@Query("query") query: String, @Query("page") page: Int = 1): Response<BaseResponse<PersonDto>>

    /**
     * Video
     * * Movie
     * * Series
     */
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(@Path("movie_id") movieId: Int): Response<VideoDto>

    @GET("tv/{tv_id}/videos")
    suspend fun getSeriesTrailer(@Path("tv_id") tvShowId: Int): Response<VideoDto>

    /**
     * Account
     * * Details
     * * Rated Movie
     * * Rated Series
     */

    @GET("account")
    suspend fun getAccountDetails(@Query("session_id") sessionId: String? = ""): Response<AccountDto>

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovie(@Path("account_id") accountId: Int = 0): Response<BaseResponse<RatedMovieDto>>

    @FormUrlEncoded
    @POST("movie/{movie_id}/rating")
    suspend fun setRateMovie(@Path("movie_id") movieId: Int, @Field("value") rating: Float): Response<RatingDto>

    @DELETE("movie/{movie_id}/rating")
    suspend fun deleteRatingMovie(@Path("movie_id") movieId: Int): Response<RatingDto>

    @FormUrlEncoded
    @POST("tv/{tv_id}/rating")
    suspend fun setRatingSeries( @Path("tv_id") seriesId: Int, @Field("value") rating: Float): Response<RatingDto>

    @DELETE("tv/{tv_id}/rating")
    suspend fun deleteRatingSeries(@Path("tv_id") seriesId: Int): Response<RatingDto>

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTvShow(@Path("account_id") listId: Int = 0): Response<BaseResponse<RatedSeriesDto>>

    /**
     * List
     * * Create
     * * Details
     * * Add Movie
     */
    @FormUrlEncoded
    @POST("list")
    suspend fun createList(
        @Query("session_id") sessionId: String,
        @Field("name") name: String,
        @Field("description") description: String = ""
    ): Response<AddListResponse>


    @GET("account/{account_id}/lists")
    suspend fun getCreatedList(
        @Path("account_id") accountId: Int = 0,
        @Query("session_id") sessionId: String
    ): Response<BaseResponse<CreatedListDto>>

    @GET("list/{list_id}")
    suspend fun getList(@Path("list_id") listId: Int): Response<FavListDto>

    @FormUrlEncoded
    @POST("list/{list_id}/add_item")
    suspend fun addMovieToFavList(@Path("list_id") listId: Int, @Query("session_id") seriesId: String, @Field("media_id") movieId: Int): Response<AddMovieDto>

    @DELETE("list/{list_id}")
    suspend fun deleteList(@Path("list_id") listId: Int): Response<StatusResponseDto>


    /**
     *  AUTHENTICATION
     * * Create Request Token
     * * Create Session (with login)
     * * Create Session
     */
    @GET("authentication/token/new")
    suspend fun getRequestToken(): Response<RequestTokenResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestTokenWithLogin(@FieldMap body: Map<String, Any>): Response<RequestTokenResponse>

    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSession(@Field("request_token") requestToken: String): Response<SessionResponse>

}


