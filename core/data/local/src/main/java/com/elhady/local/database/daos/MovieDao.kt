package com.elhady.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.local.database.entity.SearchHistoryLocalDto
import com.elhady.local.database.entity.WatchHistoryLocalDto
import com.elhady.local.database.entity.movies.AdventureMovieLocalDto
import com.elhady.local.database.entity.movies.MysteryMovieLocalDto
import com.elhady.local.database.entity.movies.NowPlayingMovieLocalDto
import com.elhady.local.database.entity.movies.PopularMovieLocalDto
import com.elhady.local.database.entity.movies.TopRatedMovieLocalDto
import com.elhady.local.database.entity.movies.TrendingMovieLocalDto
import com.elhady.local.database.entity.movies.UpcomingMovieLocalDto
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    /**
     * Popular Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(items: List<PopularMovieLocalDto>)

    @Query("DELETE FROM POPULAR_MOVIE_TABLE")
    suspend fun deletePopularMovie()

    @Query("SELECT * FROM POPULAR_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getPopularMovies(): List<PopularMovieLocalDto>

    /**
     * Trending Movies
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(items: List<TrendingMovieLocalDto>)

    @Query("DELETE FROM TRENDING_MOVIE_TABLE")
    suspend fun deleteTrendingMovies()

    @Query("SELECT * FROM TRENDING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getAllTrendingMovies(): List<TrendingMovieLocalDto>

    /**
     * Upcoming Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovie(items: List<UpcomingMovieLocalDto>)

    @Query("DELETE FROM UPCOMING_MOVIE_TABLE")
    suspend fun deleteUpcomingMovies()

    @Query("SELECT * FROM UPCOMING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getUpcomingMovies(): List<UpcomingMovieLocalDto>

    /**
     * Now Playing Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovies(items: List<NowPlayingMovieLocalDto>)

    @Query("DELETE FROM NOW_PLAYING_MOVIE_TABLE")
    suspend fun deleteNowPlayingMovies()

    @Query("SELECT * FROM NOW_PLAYING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getNowPlayingMovies(): List<NowPlayingMovieLocalDto>

    /**
     *  Top Rated Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedMovies(items: List<TopRatedMovieLocalDto>)

    @Query("DELETE FROM TOP_RATED_MOVIE_TABLE")
    suspend fun deleteTopRatedMovies()

    @Query("SELECT * FROM TOP_RATED_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getTopRatedMovies(): List<TopRatedMovieLocalDto>

    /**
     *  Mystery Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMysteryMovies(items: List<MysteryMovieLocalDto>)

    @Query("DELETE FROM MYSTERY_MOVIE_TABLE")
    suspend fun deleteMysteryMovies()

    @Query("SELECT * FROM MYSTERY_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getMysteryMovies(): List<MysteryMovieLocalDto>

    /**
     *  Adventure Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdventureMovies(items: List<AdventureMovieLocalDto>)

    @Query("DELETE FROM ADVENTURE_MOVIE_TABLE")
    suspend fun deleteAdventureMovies()

    @Query("select * from ADVENTURE_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getAdventureMovies(): List<AdventureMovieLocalDto>


    /**
     *  History Search
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryLocalDto)

    @Query("delete from SEARCH_HISTORY_TABLE where keyword like :keyword")
    suspend fun deleteSearch(keyword: String)

    @Query("delete from SEARCH_HISTORY_TABLE")
    suspend fun clearAllSearchHistory()

    @Query("select * from SEARCH_HISTORY_TABLE where keyword like :keyword")
    suspend fun getSearchHistory(keyword: String): List<SearchHistoryLocalDto>

    @Query("select * from SEARCH_HISTORY_TABLE order by keyword ASC")
    suspend fun getSearchHistory(): List<SearchHistoryLocalDto>

    /**
     *  Watch History
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatch(item: WatchHistoryLocalDto)

    @Delete
    suspend fun deleteWatch(item: WatchHistoryLocalDto)

    @Query("select * from WATCH_HISTORY_TABLE")
    fun getAllWatch(): Flow<List<WatchHistoryLocalDto>>

}