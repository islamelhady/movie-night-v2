package com.elhady.usecase.repository

import androidx.paging.Pager
import com.elhady.entities.PopularMovieEntity
import com.elhady.local.database.entity.SearchHistoryLocalDto
import com.elhady.local.database.entity.WatchHistoryLocalDto
import com.elhady.local.database.entity.movies.AdventureMovieLocalDto
import com.elhady.local.database.entity.movies.MysteryMovieLocalDto
import com.elhady.local.database.entity.movies.NowPlayingMovieLocalDto
import com.elhady.local.database.entity.movies.PopularMovieLocalDto
import com.elhady.local.database.entity.movies.TopRatedMovieLocalDto
import com.elhady.local.database.entity.movies.TrendingMovieLocalDto
import com.elhady.local.database.entity.movies.UpcomingMovieLocalDto
import com.elhady.remote.response.AddListResponse
import com.elhady.remote.response.AddMovieDto
import com.elhady.remote.response.CreatedListDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.FavListDto
import com.elhady.remote.response.RatedMovieDto
import com.elhady.remote.response.RatingDto
import com.elhady.remote.response.SavedListDto
import com.elhady.remote.response.StatusResponseDto
import com.elhady.remote.response.dto.MovieDto
import com.elhady.remote.response.genre.GenreDto
import com.elhady.remote.response.movie.MovieDetailsDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.video.VideoDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): List<PopularMovieEntity>

    suspend fun getUpcomingMovies(): List<Upmovieen>

    fun getAllUpcomingMovies(): Pager<Int, MovieDto>

    suspend fun getTopRatedMovies(): List<TopRatedMovieLocalDto>

    fun getAllTopRatedMovies(): Pager<Int, MovieDto>

    suspend fun getNowPlayingMovies(): List<NowPlayingMovieLocalDto>

    fun getAllNowPlayingMovies(): Pager<Int, MovieDto>

    suspend fun getMysteryMovies(): List<MysteryMovieLocalDto>

    fun getAllMysteryMovies(): Pager<Int, MovieDto>

    suspend fun getAdventureMovies(): List<AdventureMovieLocalDto>

    fun getAllAdventureMovies(): Pager<Int, MovieDto>

    suspend fun getTrendingMovie(): List<TrendingMovieLocalDto>

    fun getAllTrendingMovies(): Pager<Int, MovieDto>

    fun getMoviesByGenre(genreId: Int): Pager<Int, MovieDto>

    fun getAllMovies(): Pager<Int, MovieDto>

    suspend fun getGenreMovies(): List<GenreDto>?

    suspend fun getDetailsMovies(movieId: Int): MovieDetailsDto?

    suspend fun getMovieCast(movieId: Int): CreditsDto?

    suspend fun getSimilarMovies(movieId: Int): List<MovieDto>?

    suspend fun getMovieReview(movieId: Int): List<ReviewDto>?

    suspend fun searchForMoviesPager(query: String): Pager<Int, MovieDto>

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

    suspend fun setRateMovie(movieId: Int, value: Float): RatingDto?

    suspend fun deleteRateMovie(movieId: Int): RatingDto?

    suspend fun createList(sessionId: String, name: String): AddListResponse?

    suspend fun getCreatedList(sessionId: String): List<CreatedListDto>?

    suspend fun getListDetails(listId: Int): FavListDto?

    suspend fun addMovieToList(sessionId: String, listId: Int, movieId: Int): AddMovieDto?

    suspend fun getSavedListDetails(listId: Int): List<SavedListDto>?

    suspend fun deleteList(listId: Int): StatusResponseDto

}