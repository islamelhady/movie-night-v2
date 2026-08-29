package com.elhady.movies.feature.watchlist.presentation.watchhistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import com.elhady.movies.core.domain.usecase.movie.DeleteMovieFromWatchHistoryUseCase
import com.elhady.movies.core.domain.usecase.movie.GetAllWatchHistoryMoviesUseCase
import com.elhady.movies.core.domain.usecase.movie.SearchWatchHistoryUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.watchlist.presentation.watchhistory.mapper.MovieDomainMapper
import com.elhady.movies.feature.watchlist.presentation.watchhistory.mapper.MovieUiMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class WatchHistoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: WatchHistoryViewModel

    private val getAllWatchHistoryMoviesUseCase: GetAllWatchHistoryMoviesUseCase = mockk()
    private val deleteMovieFromWatchHistoryUseCase: DeleteMovieFromWatchHistoryUseCase = mockk(relaxed = true)
    private val searchWatchHistoryUseCase: SearchWatchHistoryUseCase = mockk()
    private val movieDomainMapper = MovieDomainMapper()
    private val movieUiStateMapper = MovieUiMapper()
    private val stringsRes: StringsRes = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun initViewModel(movies: List<MovieInWatchHistory> = emptyList()) {
        coEvery { getAllWatchHistoryMoviesUseCase() } returns movies
        viewModel = WatchHistoryViewModel(
            getAllWatchHistoryMoviesUseCase,
            deleteMovieFromWatchHistoryUseCase,
            searchWatchHistoryUseCase,
            movieDomainMapper,
            movieUiStateMapper,
            stringsRes
        )
    }

    @Test
    fun `MovieSwiped should remove movie from state and set pendingDeletion`() = runTest {
        // Given
        val movie = MovieInWatchHistory(1, "/path", "Title", 8.0, "Description", Date(), 2023)
        initViewModel(listOf(movie))
        advanceUntilIdle()

        // When - Swipe movie card (index 1 because index 0 is Title)
        viewModel.onEvent(WatchHistoryUiEvent.MovieSwiped(1))

        // Then
        assertEquals(0, viewModel.state.value.movies.filterIsInstance<WatchHistoryRecyclerItem.MovieCard>().size)
        assertEquals(1, viewModel.state.value.pendingDeletion?.movie?.id)
    }

    @Test
    fun `UndoDeleteClicked should restore movie to state`() = runTest {
        // Given
        val movie = MovieInWatchHistory(1, "/path", "Title", 8.0, "Description", Date(), 2023)
        initViewModel(listOf(movie))
        advanceUntilIdle()
        viewModel.onEvent(WatchHistoryUiEvent.MovieSwiped(1))

        // When
        viewModel.onEvent(WatchHistoryUiEvent.UndoDeleteClicked)

        // Then
        assertEquals(1, viewModel.state.value.movies.filterIsInstance<WatchHistoryRecyclerItem.MovieCard>().size)
        assertEquals(null, viewModel.state.value.pendingDeletion)
    }

    @Test
    fun `DeleteSnackBarDismissed should confirm deletion in usecase`() = runTest {
        // Given
        val movie = MovieInWatchHistory(1, "/path", "Title", 8.0, "Description", Date(), 2023)
        initViewModel(listOf(movie))
        advanceUntilIdle()
        viewModel.onEvent(WatchHistoryUiEvent.MovieSwiped(1))

        // When
        viewModel.onEvent(WatchHistoryUiEvent.DeleteSnackBarDismissed)
        advanceUntilIdle()

        // Then
        coVerify { deleteMovieFromWatchHistoryUseCase(any()) }
        assertEquals(null, viewModel.state.value.pendingDeletion)
    }
}
