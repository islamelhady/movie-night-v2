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
import kotlinx.coroutines.test.advanceTimeBy
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
        coEvery { getAllWatchHistoryMoviesUseCase() } returns emptyList()
        coEvery { searchWatchHistoryUseCase(any()) } returns emptyList()
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

    @Test
    fun `SearchQueryChanged should trigger search after debounce`() = runTest {
        // Given
        initViewModel()
        advanceUntilIdle() // Process initial empty search

        // When
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("Avatar"))
        
        // Then - Immediate check
        coVerify(exactly = 0) { searchWatchHistoryUseCase("Avatar") }
        
        // When - Advance time past debounce (300ms)
        advanceTimeBy(301)
        advanceUntilIdle()
        
        // Then
        coVerify(exactly = 1) { searchWatchHistoryUseCase("Avatar") }
    }

    @Test
    fun `rapid query changes should only trigger latest search`() = runTest {
        // Given
        initViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("A"))
        advanceTimeBy(100)
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("Ab"))
        advanceTimeBy(100)
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("Abc"))
        advanceTimeBy(301)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 0) { searchWatchHistoryUseCase("A") }
        coVerify(exactly = 0) { searchWatchHistoryUseCase("Ab") }
        coVerify(exactly = 1) { searchWatchHistoryUseCase("Abc") }
    }

    @Test
    fun `same query twice should not trigger second search`() = runTest {
        // Given
        initViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("Avatar"))
        advanceTimeBy(301)
        advanceUntilIdle()
        
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("Avatar"))
        advanceTimeBy(301)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { searchWatchHistoryUseCase("Avatar") }
    }

    @Test
    fun `empty query should restore full history`() = runTest {
        // Given
        initViewModel()
        advanceUntilIdle() // Initial call

        // When
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("Avatar"))
        advanceTimeBy(301)
        advanceUntilIdle()
        
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged(""))
        advanceTimeBy(301)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getAllWatchHistoryMoviesUseCase() }
    }

    @Test
    fun `slow search A should not overwrite fast search B`() = runTest {
        // Given
        initViewModel()
        advanceUntilIdle()

        coEvery { searchWatchHistoryUseCase("A") } coAnswers {
            kotlinx.coroutines.delay(2000)
            listOf(MovieInWatchHistory(1, "/A", "A", 1.0, "", Date(), 2023))
        }
        coEvery { searchWatchHistoryUseCase("B") } returns 
            listOf(MovieInWatchHistory(2, "/B", "B", 2.0, "", Date(), 2023))

        // When
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("A"))
        advanceTimeBy(301)
        // Search A starts and suspends for 2000ms
        
        viewModel.onEvent(WatchHistoryUiEvent.SearchQueryChanged("B"))
        advanceTimeBy(301)
        // Search B starts and completes immediately
        
        advanceUntilIdle()

        // Then
        val movieCards = viewModel.state.value.movies.filterIsInstance<WatchHistoryRecyclerItem.MovieCard>()
        assertEquals(1, movieCards.size)
        assertEquals("B", movieCards.first().movie.title)
    }
}
