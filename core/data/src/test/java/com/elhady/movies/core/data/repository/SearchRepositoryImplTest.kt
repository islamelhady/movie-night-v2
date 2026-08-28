package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.mapper.people.PeopleDtoMapper
import com.elhady.movies.core.data.mapper.search.MovieSearchDtoMapper
import com.elhady.movies.core.data.mapper.search.TvSearchDtoMapper
import com.elhady.movies.core.database.dao.search.SearchHistoryDao
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.api.SearchApiService
import com.elhady.movies.core.network.exception.SafeApiCaller
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchRepositoryImplTest {

    private lateinit var repository: SearchRepositoryImpl
    private val searchApiService: SearchApiService = mockk()
    private val searchHistoryDao: SearchHistoryDao = mockk()
    private val genreRepository: GenreRepository = mockk()
    private val movieSearchDtoMapper: MovieSearchDtoMapper = mockk()
    private val tvSearchDtoMapper: TvSearchDtoMapper = mockk()
    private val peopleDtoMapper: PeopleDtoMapper = mockk()
    private val safeApiCaller: SafeApiCaller = mockk()

    @Before
    fun setUp() {
        repository = SearchRepositoryImpl(
            searchApiService,
            searchHistoryDao,
            genreRepository,
            movieSearchDtoMapper,
            tvSearchDtoMapper,
            peopleDtoMapper,
            safeApiCaller
        )
    }

    @Test
    fun `searchForMovies should call API and map results`() = runTest {
        // Given
        val query = "Batman"
        coEvery { safeApiCaller.execute<com.elhady.movies.core.network.dto.common.DataWrapperResponse<com.elhady.movies.core.network.dto.movie.MovieDto>>(any()) } returns mockk(relaxed = true)
        coEvery { genreRepository.getGenresMovies() } returns emptyList()
        coEvery { movieSearchDtoMapper.map(any<List<com.elhady.movies.core.network.dto.movie.MovieDto>>(), any()) } returns emptyList()

        // When
        repository.searchForMovies(query)

        // Then
        coVerify { safeApiCaller.execute<com.elhady.movies.core.network.dto.common.DataWrapperResponse<com.elhady.movies.core.network.dto.movie.MovieDto>>(any()) }
    }

    @Test
    fun `insertSearchHistory should call DAO`() = runTest {
        // Given
        val query = "Batman"
        coEvery { searchHistoryDao.insertSearchHistory(any()) } returns Unit

        // When
        repository.insertSearchHistory(query)

        // Then
        coVerify { searchHistoryDao.insertSearchHistory(any()) }
    }

    @Test
    fun `clearAllSearchHistory should call DAO`() = runTest {
        // Given
        coEvery { searchHistoryDao.clearAllSearchHistory() } returns Unit

        // When
        repository.clearAllSearchHistory()

        // Then
        coVerify { searchHistoryDao.clearAllSearchHistory() }
    }
}
