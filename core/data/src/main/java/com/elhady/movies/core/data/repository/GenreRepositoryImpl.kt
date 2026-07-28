package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.bases.BaseRepository
import com.elhady.movies.core.data.mapper.cache.LocalGenresMovieMapper
import com.elhady.movies.core.data.mapper.cache.LocalGenresTvMapper
import com.elhady.movies.core.data.mapper.domain.DomainGenreMapper
import com.elhady.movies.core.data.mapper.domain.DomainGenreTvMapper
import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.service.MovieService
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val localGenresMovieMapper: LocalGenresMovieMapper,
    private val localGenresTvMapper: LocalGenresTvMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val domainGenreTvMapper: DomainGenreTvMapper
) : BaseRepository(), GenreRepository {

    override suspend fun getGenresMovies(): List<GenreEntity> {
        return domainGenreMapper.map(movieDao.getGenresMovies())
    }

    override suspend fun refreshGenres() {
        wrapApiCall { movieService.getListOfGenresForMovies() }.results
            ?.let { remoteGenres ->
                movieDao.insertGenresMovies(localGenresMovieMapper.map(remoteGenres))
            }
    }

    override suspend fun getGenresTvs(): List<GenreEntity> {
        return domainGenreTvMapper.map(movieDao.getGenresTvs())
    }

    override suspend fun refreshGenresTv() {
        wrapApiCall { movieService.getListOfGenresForTvs() }.results
            ?.let { remoteGenres ->
                movieDao.insertGenresTvs(localGenresTvMapper.map(remoteGenres))
            }
    }
}
