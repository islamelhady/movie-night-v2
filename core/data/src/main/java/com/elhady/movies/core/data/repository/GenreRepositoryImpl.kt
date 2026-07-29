package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.movie.LocalGenresMovieMapper
import com.elhady.movies.core.data.mapper.tvshow.LocalGenresTvMapper
import com.elhady.movies.core.data.mapper.movie.DomainGenreMapper
import com.elhady.movies.core.data.mapper.tvshow.DomainGenreTvMapper
import com.elhady.movies.core.database.dao.GenreDao
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.api.GenreApiService
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreApiService: GenreApiService,
    private val genreDao: GenreDao,
    private val localGenresMovieMapper: LocalGenresMovieMapper,
    private val localGenresTvMapper: LocalGenresTvMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val domainGenreTvMapper: DomainGenreTvMapper
) : BaseRepository(), GenreRepository {

    override suspend fun getGenresMovies(): List<Genre> {
        return domainGenreMapper.map(genreDao.getGenresMovies())
    }

    override suspend fun refreshGenres() {
        wrapApiCall { genreApiService.getListOfGenresForMovies() }.results
            ?.let { remoteGenres ->
                genreDao.insertGenresMovies(localGenresMovieMapper.map(remoteGenres))
            }
    }

    override suspend fun getGenresTvs(): List<Genre> {
        return domainGenreTvMapper.map(genreDao.getGenresTvs())
    }

    override suspend fun refreshGenresTv() {
        wrapApiCall { genreApiService.getListOfGenresForTvs() }.results
            ?.let { remoteGenres ->
                genreDao.insertGenresTvs(localGenresTvMapper.map(remoteGenres))
            }
    }
}
