package com.elhady.repository

import androidx.paging.Pager
import com.elhady.local.database.entity.SearchHistoryEntity
import com.elhady.local.database.entity.WatchHistoryEntity
import com.elhady.local.database.entity.movies.AdventureMovieEntity
import com.elhady.local.database.entity.movies.MysteryMovieEntity
import com.elhady.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.local.database.entity.movies.PopularMovieEntity
import com.elhady.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.local.database.entity.movies.TrendingMovieEntity
import com.elhady.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.remote.response.AddListResponse
import com.elhady.remote.response.AddMovieDto
import com.elhady.remote.response.CreatedListDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.FavListDto
import com.elhady.remote.response.RatedMovieDto
import com.elhady.remote.response.RatingDto
import com.elhady.remote.response.SavedListDto
import com.elhady.remote.response.dto.MovieDto
import com.elhady.remote.response.genre.GenreDto
import com.elhady.remote.response.movie.MovieDetailsDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.video.VideoDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): List<PopularMovieEntity>

    suspend fun getUpcomingMovies(): List<UpcomingMovieEntity>

    fun getAllUpcomingMovies(): Pager<Int, MovieDto>

    suspend fun getTopRatedMovies(): List<TopRatedMovieEntity>

    fun getAllTopRatedMovies(): Pager<Int, MovieDto>

    suspend fun getNowPlayingMovies(): List<NowPlayingMovieEntity>

    fun getAllNowPlayingMovies(): Pager<Int, MovieDto>

    suspend fun getMysteryMovies(): List<MysteryMovieEntity>

    fun getAllMysteryMovies(): Pager<Int, MovieDto>

    suspend fun getAdventureMovies(): List<AdventureMovieEntity>

    fun getAllAdventureMovies(): Pager<Int, MovieDto>

    suspend fun getTrendingMovie(): List<TrendingMovieEntity>

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

    suspend fun getSearchHistory(): List<SearchHistoryEntity>

    suspend fun getSearchHistory(keyword: String): List<SearchHistoryEntity>

    suspend fun clearAllSearchHistory()

    suspend fun getMovieTrailer(movieId: Int): VideoDto?

    suspend fun insertMovieWatch(movie: WatchHistoryEntity)

    suspend fun deleteMovieWatch(movie: WatchHistoryEntity)

    fun getAllMoviesWatch(): Flow<List<WatchHistoryEntity>>

    suspend fun getRatedMovie(): List<RatedMovieDto>?

//    suspend fun setRateMovie(movieId: Int, value: Float): RatingDto?

//    suspend fun deleteRateMovie(movieId: Int): RatingDto?

    suspend fun createList(sessionId: String, name: String): AddListResponse?

    suspend fun getCreatedList(sessionId: String): List<CreatedListDto>?

    suspend fun getListDetails(listId: Int): FavListDto?

    suspend fun addMovieToList(sessionId: String, listId: Int, movieId: Int): AddMovieDto?

    suspend fun getSavedListDetails(listId: Int): List<SavedListDto>?

//    suspend fun deleteList(listId: Int): StatusResponse

}