package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.common.toMediaType
import com.elhady.movies.core.data.mapper.account.CreateListDtoMapper
import com.elhady.movies.core.data.mapper.account.UserListsDtoMapper
import com.elhady.movies.core.data.mapper.common.StatusDtoMapper
import com.elhady.movies.core.data.mapper.movie.MovieDtoMapper
import com.elhady.movies.core.data.mapper.movie.MyRatedMoviesDetailsDtoMapper
import com.elhady.movies.core.data.mapper.movie.TvDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.MyRatedTvShowDtoMapper
import com.elhady.movies.core.data.paging.movie.RatedMoviesPagingSource
import com.elhady.movies.core.data.paging.tvshow.RatedTvShowPagingSource
import com.elhady.movies.core.domain.model.account.ListCreated
import com.elhady.movies.core.domain.model.account.CreateList
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
    private val createListDtoMapper: CreateListDtoMapper,
    private val ratedMoviesPagingSource: Provider<RatedMoviesPagingSource>,
    private val ratedTvShowPagingSource: Provider<RatedTvShowPagingSource>,
    private val myRatedTvShowDtoMapper: MyRatedTvShowDtoMapper,
    private val safeApiCaller: SafeApiCaller
) : AccountRepository {

    override suspend fun getUserLists(mediaId: Int?, mediaType: MediaType): List<UserList> = coroutineScope {
        val call = safeApiCaller.execute { accountApiService.getUserLists() }.results?.filterNotNull() ?: emptyList()
        val userLists = domainUserListsMapper.map(call)

        if (mediaId == null) {
            userLists
        } else {
            userLists.map { userList ->
                async {
                    try {
                        val details = getDetailsList(userList.id, mediaType)
                        userList.copy(isContainsMovie = details.any { it.id == mediaId })
                    } catch (e: Exception) {
                        userList.copy(isContainsMovie = false)
                    }
                }
            }.awaitAll()
        }
    }

    override suspend fun postUserLists(listId: Int, mediaId: Int, mediaType: MediaType): Status {
        val addMediaRequest = AddMediaToListRequest(mediaId, mediaType.value)
        val call = safeApiCaller.execute { accountApiService.postUserMedia(listId, addMediaRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun createUserList(listName: String): CreateList {
        val newList = ListRequest(name = listName)
        val call = safeApiCaller.execute { accountApiService.createUserList(newList) }
        return createListDtoMapper.map(call)
    }

    override suspend fun getFavoriteMovies(sortBy: String?): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getFavoriteMovies(sortBy) }.results
        return result?.map { item ->
            movieDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = MediaType.MOVIE,
            )
        } ?: emptyList()
    }

    override suspend fun getFavoriteTv(sortBy: String?): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getFavoriteTv(sortBy) }.results
        return result?.map { item ->
            tvDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = MediaType.TV_SHOW,
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistMovies(sortBy: String?): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getWatchlist(sortBy) }.results
        return result?.map { item ->
            movieDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = MediaType.MOVIE,
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistTv(sortBy: String?): List<Movie> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = safeApiCaller.execute { accountApiService.getWatchlistTv(sortBy) }.results
        return result?.map { item ->
            tvDtoMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = MediaType.TV_SHOW
            )
        } ?: emptyList()
    }

    override suspend fun addList(name: String): Boolean {
        return safeApiCaller.execute { accountApiService.createUserList(ListRequest(name = name)) }.success ?: false
    }

    override suspend fun getDetailsList(listId: Int, mediaType: MediaType): List<Movie> {
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
                            listType = requireNotNull(input.listType.toMediaType()),
                            name = input.name,
                            posterPath = getDetailsList(input.id ?: 0, requireNotNull(input.listType.toMediaType()))
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
        mediaType: MediaType,
        isWatchList: Boolean
    ): Status {
        val watchlistRequest = WatchlistRequest(
            mediaId = mediaId,
            mediaType = mediaType.value,
            watchlist = isWatchList
        )
        return domainStatusMapper.map(safeApiCaller.execute { accountApiService.addWatchlist(watchlistRequest) })
    }

    override suspend fun addFavouriteList(
        mediaId: Int,
        mediaType: MediaType,
        isFavourite: Boolean
    ): Status {
        val favoriteRequest = FavoriteRequest(
            mediaId = mediaId,
            mediaType = mediaType.value,
            favorite = isFavourite
        )
        return domainStatusMapper.map(safeApiCaller.execute { accountApiService.addFavorite(favoriteRequest) })
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
