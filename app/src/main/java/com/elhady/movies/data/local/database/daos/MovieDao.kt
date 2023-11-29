package com.elhady.movies.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.local.database.entity.SearchHistoryEntity
import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.data.local.database.entity.movies.AdventureMovieEntity
import com.elhady.movies.data.local.database.entity.movies.MysteryMovieEntity
import com.elhady.movies.data.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TrendingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.UpcomingMovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    /**
     * Popular Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(items: List<PopularMovieEntity>)

    @Query("DELETE FROM POPULAR_MOVIE_TABLE")
    suspend fun deletePopularMovie()

    @Query("SELECT * FROM POPULAR_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getPopularMovies(): List<PopularMovieEntity>

    /**
     * Trending Movies
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(items: List<TrendingMovieEntity>)

    @Query("DELETE FROM TRENDING_MOVIE_TABLE")
    suspend fun deleteTrendingMovies()

    @Query("SELECT * FROM TRENDING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getAllTrendingMovies(): List<TrendingMovieEntity>

    /**
     * Upcoming Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovie(items: List<UpcomingMovieEntity>)

    @Query("DELETE FROM UPCOMING_MOVIE_TABLE")
    suspend fun deleteUpcomingMovies()

    @Query("SELECT * FROM UPCOMING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getUpcomingMovies(): List<UpcomingMovieEntity>

    /**
     * Now Playing Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovies(items: List<NowPlayingMovieEntity>)

    @Query("DELETE FROM NOW_PLAYING_MOVIE_TABLE")
    suspend fun deleteNowPlayingMovies()

    @Query("SELECT * FROM NOW_PLAYING_MOVIE_TABLE")
    fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>>

    /**
     *  Top Rated Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedMovies(items: List<TopRatedMovieEntity>)

    @Query("DELETE FROM TOP_RATED_MOVIE_TABLE")
    suspend fun deleteTopRatedMovies()

    @Query("SELECT * FROM TOP_RATED_MOVIE_TABLE")
    fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>>

    /**
     *  Mystery Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMysteryMovies(items: List<MysteryMovieEntity>)

    @Query("DELETE FROM MYSTERY_MOVIE_TABLE")
    suspend fun deleteMysteryMovies()

    @Query("SELECT * FROM MYSTERY_MOVIE_TABLE")
    fun getMysteryMovies(): Flow<List<MysteryMovieEntity>>

    /**
     *  Adventure Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdventureMovies(items: List<AdventureMovieEntity>)

    @Query("DELETE FROM ADVENTURE_MOVIE_TABLE")
    suspend fun deleteAdventureMovies()

    @Query("select * from ADVENTURE_MOVIE_TABLE")
    fun getAdventureMovies(): Flow<List<AdventureMovieEntity>>


    /**
     *  History Search
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(item: SearchHistoryEntity)

    @Delete
    suspend fun deleteSearch(item: SearchHistoryEntity)

    @Query("select * from SEARCH_HISTORY_TABLE")
    fun getAllSearch(): Flow<List<SearchHistoryEntity>>

    /**
     *  Watch History
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatch(item: WatchHistoryEntity)

    @Delete
    suspend fun deleteWatch(item: WatchHistoryEntity)

    @Query("select * from WATCH_HISTORY_TABLE")
    fun getAllWatch(): Flow<List<WatchHistoryEntity>>

}