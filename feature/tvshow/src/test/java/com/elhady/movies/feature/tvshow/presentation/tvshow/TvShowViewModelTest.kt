package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetOnTheAirTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetPopularTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTopRatedTvShowsUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.tvshow.presentation.tvshow.mapper.TvShowUiMapper
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
class TvShowViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: TvShowViewModel

    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase = mockk(relaxed = true)
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCase = mockk(relaxed = true)
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase = mockk(relaxed = true)
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase = mockk(relaxed = true)
    private val stringsRes: StringsRes = mockk(relaxed = true)
    private val tvShowUiMapper = TvShowUiMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getAiringTodayTvShowsUseCase() } returns flowOf(PagingData.empty())
        coEvery { getOnTheAirTvShowsUseCase() } returns flowOf(PagingData.empty())
        coEvery { getPopularTvShowsUseCase() } returns flowOf(PagingData.empty())
        coEvery { getTopRatedTvShowsUseCase() } returns flowOf(PagingData.empty())
    }

    private fun initViewModel() {
        viewModel = TvShowViewModel(
            getAiringTodayTvShowsUseCase,
            getOnTheAirTvShowsUseCase,
            getPopularTvShowsUseCase,
            getTopRatedTvShowsUseCase,
            tvShowUiMapper,
            stringsRes,
            testDispatcher
        )
    }

    @Test
    fun `init should call getAiringTodayTvShows`() = runTest(testDispatcher) {
        initViewModel()
        advanceUntilIdle()
        coVerify { getAiringTodayTvShowsUseCase() }
        assertEquals(TvShowType.AIRING_TODAY, viewModel.state.value.tvShowType)
    }

    @Test
    fun `OnTheAirTvShowClicked should update type and call usecase`() = runTest(testDispatcher) {
        initViewModel()
        advanceUntilIdle()
        viewModel.onEvent(TvShowUiEvent.OnTheAirTvShowClicked)
        advanceUntilIdle()
        assertEquals(TvShowType.ON_THE_AIR, viewModel.state.value.tvShowType)
        coVerify { getOnTheAirTvShowsUseCase() }
    }

    @Test
    fun `PopularTvShowClicked should update type and call usecase`() = runTest(testDispatcher) {
        initViewModel()
        advanceUntilIdle()
        viewModel.onEvent(TvShowUiEvent.PopularTvShowClicked)
        advanceUntilIdle()
        assertEquals(TvShowType.POPULAR, viewModel.state.value.tvShowType)
        coVerify { getPopularTvShowsUseCase() }
    }

    @Test
    fun `TopRatedTvShowClicked should update type and call usecase`() = runTest(testDispatcher) {
        initViewModel()
        advanceUntilIdle()
        viewModel.onEvent(TvShowUiEvent.TopRatedTvShowClicked)
        advanceUntilIdle()
        assertEquals(TvShowType.TOP_RATED, viewModel.state.value.tvShowType)
        coVerify { getTopRatedTvShowsUseCase() }
    }

    @Test
    fun `RetryClicked should call appropriate usecase based on current type`() = runTest(testDispatcher) {
        initViewModel()
        advanceUntilIdle()
        viewModel.onEvent(TvShowUiEvent.PopularTvShowClicked)
        advanceUntilIdle()
        
        viewModel.onEvent(TvShowUiEvent.RetryClicked)
        advanceUntilIdle()

        coVerify(exactly = 2) { getPopularTvShowsUseCase() }
    }

    @Test
    fun `rapid category switching should cancel previous requests`() = runTest(testDispatcher) {
        // Given
        val popularPagingData = PagingData.from(listOf(mockk<TvShows>(relaxed = true)))
        val topRatedPagingData = PagingData.from(listOf(mockk<TvShows>(relaxed = true)))
        
        coEvery { getPopularTvShowsUseCase() } coAnswers {
            kotlinx.coroutines.delay(2000)
            flowOf(popularPagingData)
        }
        coEvery { getTopRatedTvShowsUseCase() } returns flowOf(topRatedPagingData)
        
        initViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(TvShowUiEvent.PopularTvShowClicked)
        testDispatcher.scheduler.advanceTimeBy(500)
        viewModel.onEvent(TvShowUiEvent.TopRatedTvShowClicked)
        advanceUntilIdle()

        // Then
        assertEquals(TvShowType.TOP_RATED, viewModel.state.value.tvShowType)
        coVerify { getTopRatedTvShowsUseCase() }
    }
}
