package com.elhady.movies.feature.watchlist.presentation.ratedmedia

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.elhady.movies.core.domain.usecase.account.GetMyRatedMoviesUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyRatedTvShowUseCase
import com.elhady.movies.feature.watchlist.presentation.ratedmedia.mapper.RatedMediaMovieToMovieHorizontalUiMapper
import com.elhady.movies.feature.watchlist.presentation.ratedmedia.mapper.RatedMediaTvShowToMovieHorizontalUiMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RatedMediaViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RatedMediaViewModel

    private val getMyRatedTvShowUseCase: GetMyRatedTvShowUseCase = mockk()
    private val getMyRatedMoviesUseCase: GetMyRatedMoviesUseCase = mockk()
    private val ratedMediaMovieMapper = RatedMediaMovieToMovieHorizontalUiMapper()
    private val ratedMediaTvShowMapper = RatedMediaTvShowToMovieHorizontalUiMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getMyRatedMoviesUseCase() } returns flowOf(PagingData.empty())
        coEvery { getMyRatedTvShowUseCase() } returns flowOf(PagingData.empty())
    }

    private fun initViewModel() {
        viewModel = RatedMediaViewModel(
            getMyRatedTvShowUseCase,
            getMyRatedMoviesUseCase,
            ratedMediaMovieMapper,
            ratedMediaTvShowMapper
        )
    }

    @Test
    fun `init should fetch rated movies by default`() = runTest {
        initViewModel()
        advanceUntilIdle()
        coVerify { getMyRatedMoviesUseCase() }
        assertEquals(RateType.Movies, viewModel.state.value.rateType)
    }

    @Test
    fun `TvShowsSelected should update type and fetch rated tv shows`() = runTest {
        initViewModel()
        advanceUntilIdle()

        viewModel.onEvent(RatedMediaUiEvent.TvShowsSelected)
        advanceUntilIdle()

        assertEquals(RateType.TvShows, viewModel.state.value.rateType)
        coVerify { getMyRatedTvShowUseCase() }
    }

    @Test
    fun `rapid category switching should cancel previous requests`() = runTest {
        // Given
        coEvery { getMyRatedMoviesUseCase() } coAnswers {
            kotlinx.coroutines.delay(2000)
            flowOf(PagingData.empty())
        }
        initViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(RatedMediaUiEvent.MoviesSelected)
        testDispatcher.scheduler.advanceTimeBy(500)
        viewModel.onEvent(RatedMediaUiEvent.TvShowsSelected)
        advanceUntilIdle()

        // Then
        assertEquals(RateType.TvShows, viewModel.state.value.rateType)
        coVerify { getMyRatedTvShowUseCase() }
    }
}
