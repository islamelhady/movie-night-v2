package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.local.database.entity.SearchHistoryEntity
import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.data.local.database.entity.movies.AdventureMovieEntity
import com.elhady.movies.data.local.database.entity.movies.MysteryMovieEntity
import com.elhady.movies.data.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TrendingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.movies.data.remote.response.AddListResponse
import com.elhady.movies.data.remote.response.AddMovieDto
import com.elhady.movies.data.remote.response.CreatedListDto
import com.elhady.movies.data.remote.response.CreditsDto
import com.elhady.movies.data.remote.response.FavListDto
import com.elhady.movies.data.remote.response.RatedMovieDto
import com.elhady.movies.data.remote.response.RatingDto
import com.elhady.movies.data.remote.response.SavedListDto
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.response.movie.MovieDetailsDto
import com.elhady.movies.data.remote.response.review.ReviewDto
import com.elhady.movies.data.remote.response.video.VideoDto
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

    suspend fun insertSearchItem(item: SearchHistoryEntity)

    suspend fun deleteSearchItem(item: SearchHistoryEntity)

    suspend fun getAllSearchItems(): List<SearchHistoryEntity>

    suspend fun getMovieTrailer(movieId: Int): VideoDto?

    suspend fun insertMovieWatch(movie: WatchHistoryEntity)

    suspend fun deleteMovieWatch(movie: WatchHistoryEntity)

    fun getAllMoviesWatch(): Flow<List<WatchHistoryEntity>>

    suspend fun getRatedMovie(): List<RatedMovieDto>?

    suspend fun setRateMovie(movieId: Int, value: Float): RatingDto?

    suspend fun deleteRateMovie(movieId: Int): RatingDto?

    suspend fun createList(sessionId: String, name: String): AddListResponse?

    suspend fun getCreatedList(sessionId: String): List<CreatedListDto>?

    suspend fun getListDetails(listId: Int): FavListDto?

    suspend fun addMovieToList(sessionId: String, listId: Int, movieId: Int): AddMovieDto?

    suspend fun getSavedListDetails(listId: Int): List<SavedListDto>?

}