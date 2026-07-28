package com.elhady.movies.core.network.api

import com.elhady.movies.core.network.model.request.AddMediaToListRequest
import com.elhady.movies.core.network.model.request.CreateUserListRequest
import com.elhady.movies.core.network.model.request.DeleteMovieRequest
import com.elhady.movies.core.network.model.request.FavoriteRequest
import com.elhady.movies.core.network.model.request.ListRequest
import com.elhady.movies.core.network.model.request.RatingRequest
import com.elhady.movies.core.network.model.request.WatchlistRequest
import com.elhady.movies.core.network.model.response.DataWrapperResponse
import com.elhady.movies.core.network.model.response.ListDetailsWrapperResponse
import com.elhady.movies.core.network.model.response.ListResponse
import com.elhady.movies.core.network.model.response.dto.ListRemoteDto
import com.elhady.movies.core.network.model.response.dto.MovieRemoteDto
import com.elhady.movies.core.network.model.response.dto.StatusResponse
import com.elhady.movies.core.network.model.response.dto.TvRemoteDto
import com.elhady.movies.core.network.model.response.dto.UserListRemoteDto
import com.elhady.movies.core.network.model.response.dto.myrated.MyRatedMovieDto
import com.elhady.movies.core.network.model.response.dto.myrated.MyRatedTvShowDto
import com.elhady.movies.core.network.model.response.dto.profile.ProfileRemoteDto
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
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String = " "
    ): Response<ProfileRemoteDto>

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
