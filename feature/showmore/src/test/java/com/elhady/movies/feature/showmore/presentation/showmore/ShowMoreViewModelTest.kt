package com.elhady.movies.feature.showmore.presentation.showmore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.elhady.movies.core.common.ShowMoreType
import com.elhady.movies.core.domain.usecase.movie.GetMorePopularMoviesByTypeUseCase
import com.elhady.movies.core.domain.usecase.movie.GetMoreTopRatedByTypeUseCase
import com.elhady.movies.core.domain.usecase.movie.GetMoreTrendingByTypeUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetOnTheAirTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetPopularTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTopRatedTvShowsUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.showmore.presentation.showmore.mapper.ShowMoreMovieUiMapper
import com.elhady.movies.feature.showmore.presentation.showmore.mapper.ShowMoreTvShowUiMapper
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShowMoreViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ShowMoreViewModel

    private val getShowMorePopularMoviesByTypeUseCase: GetMorePopularMoviesByTypeUseCase = mockk()
    private val getShowMoreTopRatedByTypeUseCase: GetMoreTopRatedByTypeUseCase = mockk()
    private val getShowMoreTrendingByTypeUseCase: GetMoreTrendingByTypeUseCase = mockk()
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase = mockk()
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase = mockk()
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase = mockk()
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCase = mockk()
    private val moviesMapper: ShowMoreMovieUiMapper = mockk()
    private val tvShowsMapper: ShowMoreTvShowUiMapper = mockk()
    private val stringsRes: StringsRes = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun initViewModel(showMoreType: ShowMoreType = ShowMoreType.POPULAR_MOVIES) {
        val savedStateHandle = SavedStateHandle(mapOf("showMoreType" to showMoreType))
        viewModel = ShowMoreViewModel(
            getShowMorePopularMoviesByTypeUseCase,
            getShowMoreTopRatedByTypeUseCase,
            getShowMoreTrendingByTypeUseCase,
            getTopRatedTvShowsUseCase,
            getAiringTodayTvShowsUseCase,
            getPopularTvShowsUseCase,
            getOnTheAirTvShowsUseCase,
            moviesMapper,
            tvShowsMapper,
            savedStateHandle,
            stringsRes
        )
    }

    @Test
    fun `init should call getPopularMoviesShowMore when type is POPULAR_MOVIES`() = runTest {
        // Given
        coEvery { getShowMorePopularMoviesByTypeUseCase() } returns flowOf(PagingData.empty())

        // When
        initViewModel(ShowMoreType.POPULAR_MOVIES)
        advanceUntilIdle()

        // Then
        coVerify { getShowMorePopularMoviesByTypeUseCase() }
    }

    @Test
    fun `init should call getTopRatedTvShow when type is TOP_RATED_TV`() = runTest {
        // Given
        coEvery { getTopRatedTvShowsUseCase() } returns flowOf(PagingData.empty())

        // When
        initViewModel(ShowMoreType.TOP_RATED_TV)
        advanceUntilIdle()

        // Then
        coVerify { getTopRatedTvShowsUseCase() }
    }

    @Test
    fun `RetryClicked should trigger data loading`() = runTest {
        // Given
        coEvery { getShowMorePopularMoviesByTypeUseCase() } returns flowOf(PagingData.empty())
        initViewModel(ShowMoreType.POPULAR_MOVIES)
        advanceUntilIdle()

        // When
        viewModel.onEvent(ShowMoreUiEvent.RetryClicked)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getShowMorePopularMoviesByTypeUseCase() }
    }
}
