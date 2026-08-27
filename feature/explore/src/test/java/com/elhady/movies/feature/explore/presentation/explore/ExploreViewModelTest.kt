package com.elhady.movies.feature.explore.presentation.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.movie.GetTrendingMoviesUseCase
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.feature.explore.presentation.explore.mapper.ExploreTrendingUiMapper
import io.mockk.coEvery
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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun initViewModel() {
        viewModel = ExploreViewModel(trendingMoviesUseCase, trendingUiMapper)
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
    fun `getTrendingMovies should emit ShowSnackBar effect on error`() = runTest {
        // Given
        val exception = AppException.NoNetwork
        coEvery { trendingMoviesUseCase() } throws exception

        val effects = mutableListOf<ExploreUiEffect>()
        val collectJob = launch {
            viewModel.effect.collect { effects.add(it) }
        }

        // When
        initViewModel()
        advanceUntilIdle()

        // Then
        assertEquals(1, effects.size)
        assertTrue(effects[0] is ExploreUiEffect.ShowSnackBar)
        assertEquals(
            ErrorUiState.NoNetwork.messageRes,
            (effects[0] as ExploreUiEffect.ShowSnackBar).messageRes
        )
        assertEquals(ErrorUiState.NoNetwork, viewModel.state.value.errors)

        collectJob.cancel()
    }
}
