package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.dto.account.AddMediaToListRequest
import com.elhady.movies.core.network.dto.account.DeleteMovieRequest
import com.elhady.movies.core.network.dto.account.FavoriteRequest
import com.elhady.movies.core.network.dto.account.ListRequest
import com.elhady.movies.core.network.dto.movie.RatingRequest
import com.elhady.movies.core.network.dto.account.WatchlistRequest
import com.elhady.movies.core.network.dto.common.DataWrapperResponse
import com.elhady.movies.core.network.dto.account.ListDetailsWrapperResponse
import com.elhady.movies.core.network.dto.account.ListResponse
import com.elhady.movies.core.network.dto.account.ListDto
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.network.dto.common.StatusResponse
import com.elhady.movies.core.network.dto.tvshow.TvDto
import com.elhady.movies.core.network.dto.account.UserListDto
import com.elhady.movies.core.network.dto.account.MyRatedMovieDto
import com.elhady.movies.core.network.dto.account.MyRatedTvShowDto
import com.elhady.movies.core.network.dto.account.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountApiService {

    @GET("account")
    suspend fun getAccountDetails(): Response<ProfileDto>

    @GET("account/account_id/lists")
    suspend fun getUserLists(): Response<DataWrapperResponse<UserListDto>>

    @POST("list/{list_id}/add_item")
    suspend fun postUserMedia(
        @Path("list_id") listId: Int,
        @Body mediaId: AddMediaToListRequest
    ): Response<StatusResponse>

    @POST("list")
    suspend fun createUserList(@Body name: ListRequest): Response<ListResponse>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(): Response<DataWrapperResponse<MovieDto>>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTv(): Response<DataWrapperResponse<TvDto>>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlist(): Response<DataWrapperResponse<MovieDto>>

    @GET("account/{account_id}/watchlist/tv")
    suspend fun getWatchlistTv(): Response<DataWrapperResponse<TvDto>>

    @POST("account/{account_id}/watchlist")
    suspend fun addWatchlist(
        @Body watchlistRequest: WatchlistRequest,
    ): Response<StatusResponse>

    @DELETE("list/{list_id}")
    suspend fun deleteList(@Path("list_id") listId: Int): Response<StatusResponse>

    @GET("account/{account_id}/lists")
    suspend fun getLists(): Response<DataWrapperResponse<ListDto>>

    @GET("list/{list_id}")
    suspend fun getDetailsList(@Path("list_id") listId: Int)
            : Response<ListDetailsWrapperResponse<MovieDto>>

    @POST("list/{list_id}/remove_item")
    suspend fun deleteMovieDetailsList(
        @Path("list_id") listId: Int,
        @Body movieRequest: DeleteMovieRequest,
    ): Response<StatusResponse>

    @POST("account/account_id/favorite")
    suspend fun addFavorite(@Body markAsFavorite: FavoriteRequest): Response<StatusResponse>

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Query("page") page: Int = 1,
    ): Response<DataWrapperResponse<MyRatedMovieDto>>

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTv(
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<MyRatedTvShowDto>>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movieId}/rating")
    suspend fun setMovieRate(
        @Body ratingRequest: RatingRequest,
        @Path("movieId") movieId: Int
    ): Response<StatusResponse>
}
