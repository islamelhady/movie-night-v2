package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.paging.movie.RatedMoviesPagingSource
import com.elhady.movies.core.data.paging.tvshow.RatedTvShowPagingSource
import com.elhady.movies.core.data.mapper.common.DomainStatusMapper
import com.elhady.movies.core.data.mapper.account.DomainUserListsMapper
import com.elhady.movies.core.data.mapper.movie.DomainMovieMapper
import com.elhady.movies.core.data.mapper.movie.DomainMyRatedMoviesDetailsMapper
import com.elhady.movies.core.data.mapper.movie.DomainTvMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainMyRatedTvShowDetailsMapper
import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.model.UserListEntity
import com.elhady.movies.core.domain.model.mylist.ListCreatedEntity
import com.elhady.movies.core.domain.model.myrated.MyRatedMovieEntity
import com.elhady.movies.core.domain.model.myrated.MyRatedTvShowEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.model.request.AddMediaToListRequest
import com.elhady.movies.core.network.model.request.CreateUserListRequest
import com.elhady.movies.core.network.model.request.DeleteMovieRequest
import com.elhady.movies.core.network.model.request.FavoriteRequest
import com.elhady.movies.core.network.model.request.ListRequest
import com.elhady.movies.core.network.model.request.RatingRequest
import com.elhady.movies.core.network.model.request.WatchlistRequest
import com.elhady.movies.core.network.service.MovieService
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val genreRepository: GenreRepository,
    private val domainMovieMapper: DomainMovieMapper,
    private val domainTvMapper: DomainTvMapper,
    private val domainStatusMapper: DomainStatusMapper,
    private val domainMyRatedMoviesDetailsMapper: DomainMyRatedMoviesDetailsMapper,
    private val domainUserListsMapper: DomainUserListsMapper,
    private val ratedMoviesPagingSource: RatedMoviesPagingSource,
    private val ratedTvShowPagingSource: RatedTvShowPagingSource,
    private val domainMyRatedTvShowDetailsMapper: DomainMyRatedTvShowDetailsMapper
) : BaseRepository(), AccountRepository {

    override suspend fun getUserLists(): List<UserListEntity> {
        val call = wrapApiCall { movieService.getUserLists() }.results?.filterNotNull() ?: emptyList()
        return domainUserListsMapper.map(call)
    }

    override suspend fun postUserLists(listId: Int, mediaId: Int): StatusEntity {
        val addMediaRequest = AddMediaToListRequest(mediaId)
        val call = wrapApiCall { movieService.postUserMedia(listId, addMediaRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun createUserList(listName: String): StatusEntity {
        val newList = CreateUserListRequest(listName)
        val call = wrapApiCall { movieService.createUserList(newList) }
        return domainStatusMapper.map(call)
    }

    override suspend fun getFavoriteMovies(): List<MovieEntity> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = wrapApiCall { movieService.getFavoriteMovies() }.results
        return result?.map { item ->
            domainMovieMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "movie",
            )
        } ?: emptyList()
    }

    override suspend fun getFavoriteTv(): List<MovieEntity> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = wrapApiCall { movieService.getFavoriteTv() }.results
        return result?.map { item ->
            domainTvMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "tv",
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistMovies(): List<MovieEntity> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = wrapApiCall { movieService.getWatchlist() }.results
        return result?.map { item ->
            domainMovieMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "movie",
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistTv(): List<MovieEntity> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = wrapApiCall { movieService.getWatchlistTv() }.results
        return result?.map { item ->
            domainTvMapper.map(
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
        return movieService.addList(ListRequest(name = name)).isSuccessful
    }

    override suspend fun getDetailsList(listId: Int): List<MovieEntity> {
        val genresEntities = genreRepository.getGenresMovies()
        val result = wrapApiCall { movieService.getDetailsList(listId) }.items
        return result?.map { item ->
            domainMovieMapper.map(
                input = item,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                )
            )
        } ?: emptyList()
    }

    override suspend fun deleteMovieDetailsList(listId: Int, mediaId: Int): StatusEntity {
        val call = wrapApiCall {
            movieService.deleteMovieDetailsList(
                listId = listId,
                DeleteMovieRequest(mediaId = mediaId)
            )
        }
        return domainStatusMapper.map(call)
    }

    override suspend fun deleteList(listId: Int): StatusEntity {
        return domainStatusMapper.map(wrapApiCall { movieService.deleteList(listId = listId) })
    }

    override suspend fun getListCreated(): List<ListCreatedEntity> {
        return wrapApiCall { movieService.getLists() }.results
            ?.filterNotNull()?.let { lists ->
                lists.map { input ->
                    ListCreatedEntity(
                        id = input.id,
                        itemCount = input.itemCount,
                        listType = input.listType,
                        name = input.name,
                        posterPath = getDetailsList(input.id ?: 0)
                            .map { items ->
                                items.imageUrl
                            }
                    )
                }
            } ?: emptyList()
    }

    override suspend fun addWatchlist(
        mediaId: Int,
        mediaType: String,
        isWatchList: Boolean
    ): StatusEntity {
        val watchlistRequest = WatchlistRequest(
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = isWatchList
        )
        val call = wrapApiCall { movieService.addWatchlist(watchlistRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun addFavouriteList(
        mediaId: Int,
        mediaType: String,
        isFavourite: Boolean
    ): StatusEntity {
        val favoriteRequest = FavoriteRequest(
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = isFavourite
        )
        val call = wrapApiCall { movieService.addFavorite(favoriteRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun setMovieRate(movieId: Int, rate: Float): StatusEntity {
        return domainStatusMapper.map(wrapApiCall {
            movieService.setMovieRate(ratingRequest = RatingRequest(rate), movieId = movieId)
        })
    }

    override suspend fun getMovieRate(): List<MyRatedMovieEntity> {
        return domainMyRatedMoviesDetailsMapper.map(
            wrapApiCall { movieService.getRatedMovies() }.results?.filterNotNull() ?: emptyList()
        )
    }

    override suspend fun getRatedMovies(): Pager<Int, MyRatedMovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ratedMoviesPagingSource }
        )
    }

    override suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShowEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ratedTvShowPagingSource }
        )
    }

    private fun filterGenres(
        genresIds: List<Int>,
        genresEntities: List<GenreEntity>
    ): List<GenreEntity> {
        return genresEntities.filter { it.genreID in genresIds }
    }
}
