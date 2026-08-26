package com.elhady.movies.core.data.repository

import androidx.room.withTransaction
import com.elhady.movies.core.data.mapper.movie.GenreEntityMapper
import com.elhady.movies.core.data.mapper.movie.GenresMovieDtoToEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.GenreTvEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.GenresTvDtoToEntityMapper
import com.elhady.movies.core.database.dao.genre.GenreMovieDao
import com.elhady.movies.core.database.dao.genre.GenreTvDao
import com.elhady.movies.core.database.db.MovieDatabase
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.api.GenreApiService
import com.elhady.movies.core.network.exception.SafeApiCaller
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreApiService: GenreApiService,
    private val genreMovieDao: GenreMovieDao,
    private val genreTvDao: GenreTvDao,
    private val genresMovieDtoToEntityMapper: GenresMovieDtoToEntityMapper,
    private val genresTvDtoToEntityMapper: GenresTvDtoToEntityMapper,
    private val genreEntityMapper: GenreEntityMapper,
    private val genreTvEntityMapper: GenreTvEntityMapper,
    private val safeApiCaller: SafeApiCaller,
    private val database: MovieDatabase
) : GenreRepository {

    override suspend fun getGenresMovies(): List<Genre> {
        return genreEntityMapper.map(genreMovieDao.getGenresMovies())
    }

    override suspend fun refreshGenres() {
        val response = safeApiCaller.execute { genreApiService.getListOfGenresForMovies() }.results
        database.withTransaction {
            response?.let { remoteGenres ->
                genreMovieDao.clearAllGenresMovies()
                genreMovieDao.insertGenresMovies(genresMovieDtoToEntityMapper.map(remoteGenres))
            }
        }
    }

    override suspend fun getGenresTvs(): List<Genre> {
        return genreTvEntityMapper.map(genreTvDao.getGenresTvs())
    }

    override suspend fun refreshGenresTv() {
        val response = safeApiCaller.execute { genreApiService.getListOfGenresForTvs() }.results
        database.withTransaction {
            response?.let { remoteGenres ->
                genreTvDao.clearAllGenresTvs()
                genreTvDao.insertGenresTvs(genresTvDtoToEntityMapper.map(remoteGenres))
            }
        }
    }
}
