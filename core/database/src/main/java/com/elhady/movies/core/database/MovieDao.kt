package com.elhady.movies.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.genre.GenresMoviesEntity
import com.elhady.movies.core.database.entity.genre.GenresTvEntity
import com.elhady.movies.core.database.entity.people.PopularPeopleEntity
import com.elhady.movies.core.database.entity.search.SearchHistoryEntity
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.database.entity.movie.MovieEntity
import com.elhady.movies.core.database.entity.movie.NowPlayingMovieEntity
import com.elhady.movies.core.database.entity.movie.PopularMovieEntity
import com.elhady.movies.core.database.entity.movie.RecommendedMovieEntity
import com.elhady.movies.core.database.entity.movie.TopRatedMovieEntity
import com.elhady.movies.core.database.entity.movie.TrendingMoviesEntity
import com.elhady.movies.core.database.entity.movie.UpcomingMovieEntity

@Dao
interface MovieDao {

    // region Movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<PopularMovieEntity>)

    @Query("select * from POPULAR_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getPopularMovies(): List<PopularMovieEntity>

    @Query("delete from POPULAR_MOVIE_TABLE")
    suspend fun clearAllPopularMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovies(movies: List<NowPlayingMovieEntity>)

    @Query("select * from NOW_PLAYING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getNowPlayingMovies(): List<NowPlayingMovieEntity>

    @Query("delete from NOW_PLAYING_MOVIE_TABLE")
    suspend fun clearAllNowPlayingMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedMovies(movies: List<TopRatedMovieEntity>)

    @Query("select * from TOP_RATED_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getTopRatedMovies(): List<TopRatedMovieEntity>

    @Query("delete from TOP_RATED_MOVIE_TABLE")
    suspend fun clearAllTopRatedMovies()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovies(movies: List<UpcomingMovieEntity>)

    @Query("select * from UPCOMING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getUpcomingMovies(): List<UpcomingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendedMovies(movies: List<RecommendedMovieEntity>)

    @Query("select * from RECOMMENDED_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getRecommendedMovie(): List<RecommendedMovieEntity>

    @Query("delete from UPCOMING_MOVIE_TABLE")
    suspend fun clearAllUpcomingMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(movies: List<TrendingMoviesEntity>)

    @Query("select * from TRENDING_MOVIES_TABLE ORDER BY RANDOM()")
    suspend fun getTrendingMovies(): List<TrendingMoviesEntity>

    @Query("delete from TRENDING_MOVIES_TABLE")
    suspend fun clearAllTrendingMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovies(movies: List<MovieEntity>)

    @Query("select * from MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getSearchMovie(): List<MovieEntity>
    // endregion


    ///region People
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularPeople(people: List<PopularPeopleEntity>)

    @Query("select * from POPULAR_PEOPLE_TABLE ORDER BY RANDOM()")
    suspend fun getPopularPeople(): List<PopularPeopleEntity>

    @Query("delete from POPULAR_PEOPLE_TABLE")
    suspend fun clearAllPopularPeople()
    /// endregion


    ///region search history
    @Query("select * from SEARCH_HISTORY_TABLE WHERE keyword LIKE :keyword")
    suspend fun getSearchHistory(keyword: String): List<SearchHistoryEntity>

    @Query("select * from SEARCH_HISTORY_TABLE ORDER BY keyword ASC LIMIT 10")
    suspend fun getSearchHistory(): List<SearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)

    @Query("delete from SEARCH_HISTORY_TABLE")
    suspend fun clearAllSearchHistory()

    @Query("delete from SEARCH_HISTORY_TABLE where keyword like :keyword")
    suspend fun deleteSearchHistory(keyword: String)
    ///endregion


    //region genres
    @Query("select * from GENRES_MOVIES_TABLE")
    suspend fun getGenresMovies(): List<GenresMoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresMovies(genresMovies: List<GenresMoviesEntity>)

    @Query("delete from GENRES_MOVIES_TABLE")
    suspend fun clearAllGenresMovies()

    @Query("select * from GENRES_TVS_TABLE")
    suspend fun getGenresTvs(): List<GenresTvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresTvs(genresMovies: List<GenresTvEntity>)

    @Query("delete from GENRES_TVS_TABLE")
    suspend fun clearAllGenresTvs()
    //endregion

    // region watch history
    @Query("select * from WATCH_HISTORY_MOVIES_TABLE")
    suspend fun getAllWatchHistoryVideos(): List<MovieInWatchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieToWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity)

    @Delete
    suspend fun deleteMovieFromWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity)

    @Query("select * from WATCH_HISTORY_MOVIES_TABLE where title like :keyword")
    suspend fun searchWatchHistory(keyword: String): List<MovieInWatchHistoryEntity>

    // endregion
}
