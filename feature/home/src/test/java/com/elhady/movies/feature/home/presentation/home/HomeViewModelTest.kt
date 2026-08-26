package com.elhady.movies.feature.home.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.movie.GetNowPlayingUseCase
import com.elhady.movies.core.domain.usecase.movie.GetPopularMoviesUseCase
import com.elhady.movies.core.domain.usecase.movie.GetTopRatedUseCase
import com.elhady.movies.core.domain.usecase.movie.GetTrendingMoviesUseCase
import com.elhady.movies.core.domain.usecase.movie.GetUpcomingMoviesUseCase
import com.elhady.movies.core.domain.usecase.people.GetPopularPeopleUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTvUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowUseCase
import com.elhady.movies.feature.home.presentation.home.mapper.AiringTodayUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.NowPlayingUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.PopularMoviesUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.PopularPeopleUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.TopRatedUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.TrendingUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.TvShowUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.UpComingUiMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    private val nowPlayingUseCase: GetNowPlayingUseCase = mockk()
    private val popularMoviesUseCase: GetPopularMoviesUseCase = mockk()
    private val popularPeopleUseCase: GetPopularPeopleUseCase = mockk()
    private val topRatedUseCase: GetTopRatedUseCase = mockk()
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase = mockk()
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase = mockk()
    private val tvShowUseCase: GetTvShowUseCase = mockk()
    private val getAiringTodayTvUseCase: GetAiringTodayTvUseCase = mockk()

    private val upComingUiMapper = UpComingUiMapper()
    private val nowPlayingUiMapper = NowPlayingUiMapper()
    private val trendingUiMapper = TrendingUiMapper()
    private val topRatedUiMapper = TopRatedUiMapper()
    private val tvShowUiMapper = TvShowUiMapper()
    private val popularPeopleUiMapper = PopularPeopleUiMapper()
    private val popularMoviesUiMapper = PopularMoviesUiMapper()
    private val airingTodayUiMapper = AiringTodayUiMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        
        // Default mocks
        coEvery { upcomingMoviesUseCase() } returns emptyList()
        coEvery { popularPeopleUseCase() } returns emptyList()
        coEvery { tvShowUseCase() } returns emptyList()
        coEvery { nowPlayingUseCase() } returns emptyList()
        coEvery { trendingMoviesUseCase() } returns emptyList()
        coEvery { popularMoviesUseCase() } returns emptyList()
        coEvery { topRatedUseCase() } returns emptyList()
        coEvery { getAiringTodayTvUseCase() } returns emptyList()
    }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            nowPlayingUseCase, popularMoviesUseCase, popularPeopleUseCase,
            topRatedUseCase, trendingMoviesUseCase, upcomingMoviesUseCase,
            tvShowUseCase, getAiringTodayTvUseCase,
            upComingUiMapper, nowPlayingUiMapper, trendingUiMapper,
            topRatedUiMapper, tvShowUiMapper, popularPeopleUiMapper,
            popularMoviesUiMapper, airingTodayUiMapper
        )
    }

    @Test
    fun `getData should update state with movies when successful`() = runTest {
        // Given
        coEvery { upcomingMoviesUseCase() } returns listOf(mockk(relaxed = true))
        
        // When
        initViewModel()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.upcomingMovies.isNotEmpty())
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.hasData)
    }

    @Test
    fun `getData should show error when all sections fail`() = runTest {
        // Given
        coEvery { upcomingMoviesUseCase() } throws AppException.NoNetwork
        coEvery { popularPeopleUseCase() } throws AppException.NoNetwork
        coEvery { tvShowUseCase() } throws AppException.NoNetwork
        coEvery { nowPlayingUseCase() } throws AppException.NoNetwork
        coEvery { trendingMoviesUseCase() } throws AppException.NoNetwork
        coEvery { popularMoviesUseCase() } throws AppException.NoNetwork
        coEvery { topRatedUseCase() } throws AppException.NoNetwork
        coEvery { getAiringTodayTvUseCase() } throws AppException.NoNetwork

        // When
        initViewModel()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.isError)
        assertFalse(viewModel.state.value.hasData)
    }

    @Test
    fun `getData should preserve successful data when one section fails`() = runTest {
        // Given
        coEvery { upcomingMoviesUseCase() } returns listOf(mockk(relaxed = true))
        coEvery { popularMoviesUseCase() } throws AppException.NoNetwork

        // When
        initViewModel()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.upcomingMovies.isNotEmpty())
        assertTrue(viewModel.state.value.isError)
        assertTrue(viewModel.state.value.hasData)
    }
}
