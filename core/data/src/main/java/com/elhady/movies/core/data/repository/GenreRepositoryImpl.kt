package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.movie.GenresMovieDtoToEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.GenresTvDtoToEntityMapper
import com.elhady.movies.core.data.mapper.movie.GenreEntityMapper
import com.elhady.movies.core.data.mapper.tvshow.GenreTvEntityMapper
import com.elhady.movies.core.database.dao.GenreDao
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.api.GenreApiService
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreApiService: GenreApiService,
    private val genreDao: GenreDao,
    private val genresMovieDtoToEntityMapper: GenresMovieDtoToEntityMapper,
    private val genresTvDtoToEntityMapper: GenresTvDtoToEntityMapper,
    private val genreEntityMapper: GenreEntityMapper,
    private val genreTvEntityMapper: GenreTvEntityMapper
) : BaseRepository(), GenreRepository {

    override suspend fun getGenresMovies(): List<Genre> {
        return genreEntityMapper.map(genreDao.getGenresMovies())
    }

    override suspend fun refreshGenres() {
        wrapApiCall { genreApiService.getListOfGenresForMovies() }.results
            ?.let { remoteGenres ->
                genreDao.insertGenresMovies(genresMovieDtoToEntityMapper.map(remoteGenres))
            }
    }

    override suspend fun getGenresTvs(): List<Genre> {
        return genreTvEntityMapper.map(genreDao.getGenresTvs())
    }

    override suspend fun refreshGenresTv() {
        wrapApiCall { genreApiService.getListOfGenresForTvs() }.results
            ?.let { remoteGenres ->
                genreDao.insertGenresTvs(genresTvDtoToEntityMapper.map(remoteGenres))
            }
    }
}
