package com.elhady.usecase.repository

import androidx.paging.Pager
import com.elhady.entities.GenreEntity
import com.elhady.entities.MovieEntity
import com.elhady.entities.PopularMovieEntity
import com.elhady.local.database.dto.SearchHistoryLocalDto
import com.elhady.local.database.dto.WatchHistoryLocalDto
import com.elhady.local.database.dto.movies.AdventureMovieLocalDto
import com.elhady.local.database.dto.movies.MysteryMovieLocalDto
import com.elhady.local.database.dto.movies.NowPlayingMovieLocalDto
import com.elhady.local.database.dto.movies.TrendingMovieLocalDto
import com.elhady.remote.response.AddListResponse
import com.elhady.remote.response.AddMovieDto
import com.elhady.remote.response.CreatedListDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.FavListDto
import com.elhady.remote.response.RatedMovieDto
import com.elhady.remote.response.StatusResponse
import com.elhady.remote.response.SavedListDto
import com.elhady.remote.response.StatusResponseDto
import com.elhady.remote.response.dto.MovieRemoteDto
import com.elhady.remote.response.movie.MovieDetailsDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.video.VideoDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMoviesFromRemote(): List<PopularMovieEntity>
    suspend fun getPopularMoviesFromDatabase(): List<PopularMovieEntity>
    suspend fun refreshPopularMovies()

    suspend fun getUpcomingMoviesFromDatabase(): List<MovieEntity>
    suspend fun refreshUpcomingMovies()
    fun getUpcomingMoviesPaging(): Pager<Int, MovieEntity>

    suspend fun refreshTopRatedMovies()
    suspend fun getTopRatedMoviesFromDatabase(): List<MovieEntity>
    fun getAllTopRatedMovies(): Pager<Int, MovieRemoteDto>

    suspend fun getNowPlayingMoviesFromDatabase(): List<MovieEntity>
    suspend fun refreshNowPlayingMovies()
    fun getAllNowPlayingMovies(): Pager<Int, MovieRemoteDto>

    suspend fun getMysteryMoviesFromDatabase(): List<MovieEntity>
    suspend fun refreshMysteryMovies()
    fun getAllMysteryMovies(): Pager<Int, MovieRemoteDto>

    suspend fun getAdventureMovies(): List<AdventureMovieLocalDto>

    fun getAllAdventureMovies(): Pager<Int, MovieRemoteDto>

    suspend fun getTrendingMovie(): List<TrendingMovieLocalDto>

    fun getAllTrendingMovies(): Pager<Int, MovieRemoteDto>

    fun getMoviesByGenre(genreId: Int): Pager<Int, MovieRemoteDto>

    fun getAllMovies(): Pager<Int, MovieRemoteDto>

    suspend fun getGenreMovies(): List<GenreEntity>
    suspend fun refreshGenres()

    suspend fun getDetailsMovies(movieId: Int): MovieDetailsDto?

    suspend fun getMovieCast(movieId: Int): CreditsDto?

    suspend fun getSimilarMovies(movieId: Int): List<MovieRemoteDto>?

    suspend fun getMovieReview(movieId: Int): List<ReviewDto>?

    suspend fun searchForMoviesPager(query: String): Pager<Int, MovieRemoteDto>

    suspend fun insertSearchHistory(searchHistory: String)

    suspend fun deleteSearchHistory(keyword: String)

    suspend fun getSearchHistory(): List<SearchHistoryLocalDto>

    suspend fun getSearchHistory(keyword: String): List<SearchHistoryLocalDto>

    suspend fun clearAllSearchHistory()

    suspend fun getMovieTrailer(movieId: Int): VideoDto?

    suspend fun insertMovieWatch(movie: WatchHistoryLocalDto)

    suspend fun deleteMovieWatch(movie: WatchHistoryLocalDto)

    fun getAllMoviesWatch(): Flow<List<WatchHistoryLocalDto>>

    suspend fun getRatedMovie(): List<RatedMovieDto>?

    suspend fun setRateMovie(movieId: Int, value: Float): StatusResponse?

    suspend fun deleteRateMovie(movieId: Int): StatusResponse?

    suspend fun createList(sessionId: String, name: String): AddListResponse?

    suspend fun getCreatedList(sessionId: String): List<CreatedListDto>?

    suspend fun getListDetails(listId: Int): FavListDto?

    suspend fun addMovieToList(sessionId: String, listId: Int, movieId: Int): AddMovieDto?

    suspend fun getSavedListDetails(listId: Int): List<SavedListDto>?

    suspend fun deleteList(listId: Int): StatusResponseDto

}