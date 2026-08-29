package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.data.mapper.account.UserListsDtoMapper
import com.elhady.movies.core.data.mapper.common.StatusDtoMapper
import com.elhady.movies.core.data.mapper.movie.MovieDtoMapper
import com.elhady.movies.core.data.mapper.movie.MyRatedMoviesDetailsDtoMapper
import com.elhady.movies.core.data.mapper.movie.TvDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.MyRatedTvShowDtoMapper
import com.elhady.movies.core.data.paging.movie.RatedMoviesPagingSource
import com.elhady.movies.core.data.paging.tvshow.RatedTvShowPagingSource
import com.elhady.movies.core.domain.model.account.ListCreated
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.domain.model.account.UserList
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.AccountRepository
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.network.dto.account.AddMediaToListRequest
import com.elhady.movies.core.network.dto.account.CreateUserListRequest
import com.elhady.movies.core.network.dto.account.DeleteMovieRequest
import com.elhady.movies.core.network.dto.account.FavoriteRequest
import com.elhady.movies.core.network.dto.account.ListRequest
import com.elhady.movies.core.network.dto.account.WatchlistRequest
import com.elhady.movies.core.network.dto.movie.RatingRequest
import com.elhady.movies.core.network.exception.SafeApiCaller
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Provider

class AccountRepositoryImpl @Inject constructor(
    private val accountApiService: AccountApiService,
    private val genreRepository: GenreRepository,
    private val movieDtoMapper: MovieDtoMapper,
    private val tvDtoMapper: TvDtoMapper,
    private val domainStatusMapper: StatusDtoMapper,
    private val myRatedMoviesDetailsDtoMapper: MyRatedMoviesDetailsDtoMapper,
    private val domainUserListsMapper: UserListsDtoMapper,
    private val ratedMoviesPagingSource: Provider<RatedMoviesPagingSource>,
    private val ratedTvShowPagingSource: Provider<RatedTvShowPagingSource>,
    private val myRatedTvShowDtoMapper: MyRatedTvShowDtoMapper,
    private val safeApiCaller: SafeApiCaller
) : AccountRepository {

    override suspend fun getUserLists(): List<UserList> {
        val call = safeApiCaller.execute { accountApiService.getUserLists() }.results?.filterNotNull() ?: emptyList()
        return domainUserListsMapper.map(call)
    }

    override suspend fun postUserLists(listId: Int, mediaId: Int, mediaType: String): Status {
        val addMediaRequest = AddMediaToListRequest(mediaId, mediaType)
        val call = safeApiCaller.execute { accountApiService.postUserMedia(listId, addMediaRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun createUserList(listName: String): Status {
        val newList = CreateUserListRequest(listName)
        val call = safeApiCaller.execute { accountApiService.createUserList(newList) }
        return domainStatusMapper.map(call)
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getFavoriteMovies() }.results
        return result?.map { item ->
            movieDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "movie",
            )
        } ?: emptyList()
    }

    override suspend fun getFavoriteTv(): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getFavoriteTv() }.results
        return result?.map { item ->
            tvDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "tv",
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistMovies(): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getWatchlist() }.results
        return result?.map { item ->
            movieDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "movie",
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistTv(): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getWatchlistTv() }.results
        return result?.map { item ->
            tvDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "tv"
            )
        } ?: emptyList()
    }

    override suspend fun addList(name: String): Boolean {
        return accountApiService.addList(ListRequest(name = name)).isSuccessful
    }

    override suspend fun getDetailsList(listId: Int, mediaType: String): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getDetailsList(listId) }.items
        return result?.map { item ->
            movieDtoMapper.map(
                input = item,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = mediaType
            )
        } ?: emptyList()
    }

    override suspend fun deleteMovieDetailsList(listId: Int, mediaId: Int): Status {
        val call = safeApiCaller.execute {
            accountApiService.deleteMovieDetailsList(
                listId = listId,
                DeleteMovieRequest(mediaId = mediaId)
            )
        }
        return domainStatusMapper.map(call)
    }

    override suspend fun deleteList(listId: Int): Status {
        return domainStatusMapper.map(safeApiCaller.execute { accountApiService.deleteList(listId = listId) })
    }

    override suspend fun getListCreated(): List<ListCreated> = coroutineScope {
        safeApiCaller.execute { accountApiService.getLists() }.results
            ?.filterNotNull()?.let { lists ->
                lists.map { input ->
                    async {
                        ListCreated(
                            id = input.id,
                            itemCount = input.itemCount,
                            listType = input.listType,
                            name = input.name,
                            posterPath = getDetailsList(input.id ?: 0, input.listType ?: "movie")
                                .map { items ->
                                    items.imageUrl
                                }
                        )
                    }
                }.awaitAll()
            } ?: emptyList()
    }

    override suspend fun addWatchlist(
        mediaId: Int,
        mediaType: String,
        isWatchList: Boolean
    ): Status {
        val watchlistRequest = WatchlistRequest(
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = isWatchList
        )
        val call = safeApiCaller.execute { accountApiService.addWatchlist(watchlistRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun addFavouriteList(
        mediaId: Int,
        mediaType: String,
        isFavourite: Boolean
    ): Status {
        val favoriteRequest = FavoriteRequest(
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = isFavourite
        )
        val call = safeApiCaller.execute { accountApiService.addFavorite(favoriteRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun setMovieRate(movieId: Int, rate: Float): Status {
        return domainStatusMapper.map(safeApiCaller.execute {
            accountApiService.setMovieRate(ratingRequest = RatingRequest(rate), movieId = movieId)
        })
    }

    override suspend fun getMovieRate(): List<MyRatedMovie> {
        return myRatedMoviesDetailsDtoMapper.map(
            safeApiCaller.execute { accountApiService.getRatedMovies() }.results?.filterNotNull() ?: emptyList()
        )
    }

    override suspend fun getRatedMovies(): Pager<Int, MyRatedMovie> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = ::createRatedMoviesPagingSource
        )
    }

    internal fun createRatedMoviesPagingSource(): RatedMoviesPagingSource {
        return ratedMoviesPagingSource.get()
    }

    override suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShow> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = ::createRatedTvShowPagingSource
        )
    }

    internal fun createRatedTvShowPagingSource(): RatedTvShowPagingSource {
        return ratedTvShowPagingSource.get()
    }

    private fun filterGenres(
        genresIds: List<Int>,
        genresEntities: List<Genre>
    ): List<Genre> {
        return genresEntities.filter { it.genreID in genresIds }
    }
}
