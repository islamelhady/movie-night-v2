package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.mapper.people.PeopleDtoMapper
import com.elhady.movies.core.data.mapper.search.MovieSearchDtoMapper
import com.elhady.movies.core.data.mapper.search.TvSearchDtoMapper
import com.elhady.movies.core.database.dao.search.SearchHistoryDao
import com.elhady.movies.core.database.entity.search.SearchHistoryEntity
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.Tv
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.domain.repository.SearchRepository
import com.elhady.movies.core.network.api.SearchApiService
import com.elhady.movies.core.network.exception.SafeApiCaller
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService,
    private val searchHistoryDao: SearchHistoryDao,
    private val genreRepository: GenreRepository,
    private val movieSearchDtoMapper: MovieSearchDtoMapper,
    private val tvSearchDtoMapper: TvSearchDtoMapper,
    private val peopleDtoMapper: PeopleDtoMapper,
    private val safeApiCaller: SafeApiCaller
) :SearchRepository {

    override suspend fun getSearchHistory(keyword: String): List<String> {
        return searchHistoryDao.getSearchHistory("%${keyword}%").map { it.keyword }
    }

    override suspend fun searchHistory(): List<String> {
        return searchHistoryDao.getSearchHistory().map { it.keyword }
    }

    override suspend fun insertSearchHistory(keyword: String) {
        searchHistoryDao.insertSearchHistory(SearchHistoryEntity(keyword))
    }

    override suspend fun clearAllSearchHistory() {
        searchHistoryDao.clearAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(keyword: String) {
        searchHistoryDao.deleteSearchHistory(keyword)
    }

    override suspend fun searchForMovies(keyword: String): List<Movie> {
        val movieDto =
            safeApiCaller.execute { searchApiService.searchForMovies(keyword) }.results?.filterNotNull()
                ?: emptyList()
        val genresEntities = genreRepository.getGenresMovies()
        return movieSearchDtoMapper.map(movieDto, genresEntities)
    }

    override suspend fun searchForTv(keyword: String): List<Tv> {
        val tvDto = safeApiCaller.execute { searchApiService.searchForTv(keyword) }.results?.filterNotNull()
            ?: emptyList()
        val genresTvEntities = genreRepository.getGenresTvs()
        return tvSearchDtoMapper.map(tvDto, genresTvEntities)
    }

    override suspend fun searchForPeople(keyword: String): List<People> {
        return peopleDtoMapper.map(
            safeApiCaller.execute { searchApiService.searchForPeople(keyword) }.results?.filterNotNull()
                ?: emptyList()
        )
    }
}
