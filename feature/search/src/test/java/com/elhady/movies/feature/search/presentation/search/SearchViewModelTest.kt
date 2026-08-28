package com.elhady.movies.feature.search.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elhady.movies.core.domain.usecase.movie.GetAllGenresMoviesUseCase
import com.elhady.movies.core.domain.usecase.search.InsertSearchHistoryUseCase
import com.elhady.movies.core.domain.usecase.search.SearchHistoryUseCase
import com.elhady.movies.core.domain.usecase.search.SearchMoviesUseCase
import com.elhady.movies.core.domain.usecase.search.SearchPeopleUseCase
import com.elhady.movies.core.domain.usecase.search.SearchTvsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetAllGenresTvsUseCase
import com.elhady.movies.feature.search.presentation.search.mapper.GenreUiMapper
import com.elhady.movies.feature.search.presentation.search.mapper.MovieUiMapper
import com.elhady.movies.feature.search.presentation.search.mapper.PeopleUiMapper
import com.elhady.movies.feature.search.presentation.search.mapper.TvUiMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SearchViewModel

    private val getAllGenresMoviesUseCase: GetAllGenresMoviesUseCase = mockk()
    private val getAllGenresTvsUseCase: GetAllGenresTvsUseCase = mockk()
    private val searchMoviesUseCase: SearchMoviesUseCase = mockk()
    private val searchTvsUseCase: SearchTvsUseCase = mockk()
    private val searchPeopleUseCase: SearchPeopleUseCase = mockk()
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase = mockk()
    private val searchHistoryUseCase: SearchHistoryUseCase = mockk()
    private val genreUiStateMapper = GenreUiMapper()
    private val movieUiMapper = MovieUiMapper()
    private val tvUiMapper = TvUiMapper()
    private val peopleUiMapper = PeopleUiMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { searchHistoryUseCase(any()) } returns emptyList()
        coEvery { searchMoviesUseCase(any(), any()) } returns emptyList()
        coEvery { searchTvsUseCase(any(), any()) } returns emptyList()
        coEvery { searchPeopleUseCase(any()) } returns emptyList()
        coEvery { insertSearchHistoryUseCase(any()) } returns Unit
    }

    private fun initViewModel() {
        viewModel = SearchViewModel(
            getAllGenresMoviesUseCase,
            getAllGenresTvsUseCase,
            searchMoviesUseCase,
            searchTvsUseCase,
            searchPeopleUseCase,
            insertSearchHistoryUseCase,
            searchHistoryUseCase,
            genreUiStateMapper,
            movieUiMapper,
            tvUiMapper,
            peopleUiMapper
        )
    }

    @Test
    fun `onQueryChanged should update state and set loading`() = runTest {
        // Given
        initViewModel()

        // When
        viewModel.onEvent(SearchUiEvent.QueryChanged("Batman"))
        
        // Then
        assertEquals("Batman", viewModel.state.value.searchQuery)
        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `history should be saved only after successful search`() = runTest {
        // Given
        initViewModel()
        advanceUntilIdle() // Process init

        // When
        viewModel.onEvent(SearchUiEvent.QueryChanged("Batman"))
        advanceTimeBy(600) // Trigger debounce (500ms)
        advanceUntilIdle() // Process background work

        // Then
        coVerify { insertSearchHistoryUseCase("Batman") }
    }

    @Test
    fun `rapid typing should cancel previous search jobs`() = runTest {
        // Given
        coEvery { searchMoviesUseCase(any(), any()) } coAnswers {
            kotlinx.coroutines.delay(2000)
            emptyList()
        }
        initViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(SearchUiEvent.QueryChanged("A"))
        advanceTimeBy(600) // Trigger first search
        
        viewModel.onEvent(SearchUiEvent.QueryChanged("B"))
        advanceTimeBy(600) // Trigger second search
        
        advanceUntilIdle()

        // Then
        coVerify { searchMoviesUseCase("B", any()) }
    }
}
