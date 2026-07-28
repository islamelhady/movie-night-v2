package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.people.DomainPeopleRemoteMapper
import com.elhady.movies.core.data.mapper.search.DomainMovieSearchMapper
import com.elhady.movies.core.data.mapper.search.DomainTvShowSearchMapper
import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.database.dto.SearchHistoryLocalDto
import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.core.domain.model.people.PeopleEntity
import com.elhady.movies.core.domain.model.tvshow.TvEntity
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.domain.repository.SearchRepository
import com.elhady.movies.core.network.api.SearchApiService
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService,
    private val movieDao: MovieDao,
    private val genreRepository: GenreRepository,
    private val domainMovieSearchMapper: DomainMovieSearchMapper,
    private val domainTvShowSearchMapper: DomainTvShowSearchMapper,
    private val domainPeopleRemoteMapper: DomainPeopleRemoteMapper
) : BaseRepository(), SearchRepository {

    override suspend fun getSearchHistory(keyword: String): List<String> {
        return movieDao.getSearchHistory("%${keyword}%").map { it.keyword }
    }

    override suspend fun searchHistory(): List<String> {
        return movieDao.getSearchHistory().map { it.keyword }
    }

    override suspend fun insertSearchHistory(keyword: String) {
        movieDao.insertSearchHistory(SearchHistoryLocalDto(keyword))
    }

    override suspend fun clearAllSearchHistory() {
        movieDao.clearAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(keyword: String) {
        movieDao.deleteSearchHistory(keyword)
    }

    override suspend fun searchForMovies(keyword: String): List<MovieEntity> {
        val movieRemoteDto =
            wrapApiCall { searchApiService.searchForMovies(keyword) }.results?.filterNotNull()
                ?: emptyList()
        val genresEntities = genreRepository.getGenresMovies()
        return domainMovieSearchMapper.map(movieRemoteDto, genresEntities)
    }

    override suspend fun searchForTv(keyword: String): List<TvEntity> {
        val tvRemoteDto = wrapApiCall { searchApiService.searchForTv(keyword) }.results?.filterNotNull()
            ?: emptyList()
        val genresTvEntities = genreRepository.getGenresTvs()
        return domainTvShowSearchMapper.map(tvRemoteDto, genresTvEntities)
    }

    override suspend fun searchForPeople(keyword: String): List<PeopleEntity> {
        return domainPeopleRemoteMapper.map(
            wrapApiCall { searchApiService.searchForPeople(keyword) }.results?.filterNotNull()
                ?: emptyList()
        )
    }
}
