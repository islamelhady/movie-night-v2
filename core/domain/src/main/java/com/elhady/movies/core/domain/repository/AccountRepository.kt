package com.elhady.movies.core.domain.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.domain.model.account.UserList
import com.elhady.movies.core.domain.model.account.CreateList
import com.elhady.movies.core.domain.model.account.ListCreated
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import com.elhady.movies.core.domain.model.account.MyRatedTvShow

interface AccountRepository {
    suspend fun getUserLists(mediaId: Int? = null, mediaType: MediaType = MediaType.MOVIE): List<UserList>
    suspend fun postUserLists(listId: Int, mediaId: Int, mediaType: MediaType): Status
    suspend fun createUserList(listName: String): CreateList

    suspend fun getFavoriteMovies(): List<Movie>
    suspend fun getFavoriteTv(): List<Movie>
    suspend fun getWatchlistMovies(): List<Movie>
    suspend fun getWatchlistTv(): List<Movie>

    suspend fun addList(name: String): Boolean
    suspend fun getDetailsList(listId: Int, mediaType: MediaType): List<Movie>
    suspend fun deleteMovieDetailsList(listId: Int, mediaId: Int): Status
    suspend fun deleteList(listId: Int): Status
    suspend fun getListCreated(): List<ListCreated>

    suspend fun addWatchlist(mediaId: Int, mediaType: MediaType, isWatchList: Boolean): Status
    suspend fun addFavouriteList(mediaId: Int, mediaType: MediaType, isFavourite: Boolean): Status

    suspend fun setMovieRate(movieId: Int, rate: Float): Status
    suspend fun getMovieRate(): List<MyRatedMovie>
    suspend fun getRatedMovies(): Pager<Int, MyRatedMovie>
    suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShow>
}
