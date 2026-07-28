package com.elhady.movies.core.domain.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.model.UserListEntity
import com.elhady.movies.core.domain.model.mylist.ListCreatedEntity
import com.elhady.movies.core.domain.model.myrated.MyRatedMovieEntity
import com.elhady.movies.core.domain.model.myrated.MyRatedTvShowEntity

interface AccountRepository {
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
    suspend fun addFavouriteList(mediaId: Int, mediaType: String, isFavourite: Boolean): StatusEntity

    suspend fun setMovieRate(movieId: Int, rate: Float): StatusEntity
    suspend fun getMovieRate(): List<MyRatedMovieEntity>
    suspend fun getRatedMovies(): Pager<Int, MyRatedMovieEntity>
    suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShowEntity>
}
