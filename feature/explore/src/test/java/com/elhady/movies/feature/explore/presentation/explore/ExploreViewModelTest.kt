package com.elhady.movies.feature.explore.presentation.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.movie.GetTrendingMoviesUseCase
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.UiText
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.explore.presentation.explore.mapper.ExploreTrendingUiMapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ExploreViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ExploreViewModel

    private val trendingMoviesUseCase: GetTrendingMoviesUseCase = mockk()
    private val trendingUiMapper = ExploreTrendingUiMapper()
    private val stringsRes: StringsRes = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { stringsRes.noNetworkConnection } returns UiText.Dynamic("No Network")
        every { stringsRes.timeOut } returns UiText.Dynamic("Timeout")
        every { stringsRes.someThingError } returns UiText.Dynamic("Something went wrong")
    }

    private fun initViewModel() {
        viewModel = ExploreViewModel(trendingMoviesUseCase, trendingUiMapper, stringsRes)
    }

    @Test
    fun `initial state should have loading true after init`() = runTest {
        coEvery { trendingMoviesUseCase() } returns emptyList()
        initViewModel()

        assertEquals(true, viewModel.state.value.isLoading)
    }

    @Test
    fun `getTrendingMovies should update state on success`() = runTest {
        // Given
        val movies = listOf(
            com.elhady.movies.core.domain.model.movie.Movie(
                id = 1,
                title = "Movie 1",
                imageUrl = "url",
                genreEntities = emptyList(),
                rate = 8.0,
                year = "2023-01-01"
            )
        )
        coEvery { trendingMoviesUseCase() } returns movies

        // When
        initViewModel()
        advanceUntilIdle()

        // Then
        assertEquals(1, viewModel.state.value.trendingMoviesToday.size)
        assertEquals("Movie 1", viewModel.state.value.trendingMoviesToday[0].title)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `getTrendingMovies should show screen error when no data and fails`() = runTest {
        // Given
        val exception = AppException.NoNetwork
        coEvery { trendingMoviesUseCase() } throws exception

        // When
        initViewModel()
        advanceUntilIdle()

        // Then
        assertEquals(ErrorUiState.NoNetwork, viewModel.state.value.errors)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `getTrendingMovies should emit ShowSnackBar effect when data exists and fails`() = runTest {
        // Given
        val movies = listOf(
            com.elhady.movies.core.domain.model.movie.Movie(
                id = 1,
                title = "Movie 1",
                imageUrl = "url",
                genreEntities = emptyList(),
                rate = 8.0,
                year = "2023-01-01"
            )
        )
        coEvery { trendingMoviesUseCase() } returns movies

        initViewModel()
        advanceUntilIdle()
        
        // Fails on subsequent request
        coEvery { trendingMoviesUseCase() } throws AppException.NoNetwork

        val effects = mutableListOf<ExploreUiEffect>()
        val collectJob = launch {
            viewModel.effect.collect { effects.add(it) }
        }

        // When
        viewModel.onEvent(ExploreUiEvent.RetryClicked)
        advanceUntilIdle()

        // Then
        assertEquals(1, effects.size)
        assertTrue(effects[0] is ExploreUiEffect.ShowSnackBar)
        assertEquals(UiText.Dynamic("No Network"), (effects[0] as ExploreUiEffect.ShowSnackBar).message)
        // Screen error should remain null if data exists
        assertEquals(null, viewModel.state.value.errors)

        collectJob.cancel()
    }
}
