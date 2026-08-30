package com.elhady.movies.feature.watchlist.presentation.listcontents

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.DeleteMovieFromDetailsListUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyFavoriteListUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyListDetailsByListIdUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyWatchlistListUseCase
import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.watchlist.presentation.listcontents.mapper.ListContentsUiMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListContentsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ListContentsViewModel

    private val stringsRes: StringsRes = mockk(relaxed = true)
    private val getFavoriteUseCase: GetMyFavoriteListUseCase = mockk()
    private val getWatchlistUseCase: GetMyWatchlistListUseCase = mockk()
    private val getMovieListDetailsUseCase: GetMyListDetailsByListIdUseCase = mockk()
    private val deleteFavoriteUseCase: AddToFavouriteUseCase = mockk()
    private val deleteMovieFromDetailsListUseCase: DeleteMovieFromDetailsListUseCase = mockk()
    private val deleteWatchlistUseCase: AddToWatchList = mockk()
    private val listContentsUiMapper = ListContentsUiMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
    }

    @After
    fun tearDown() {
        unmockkStatic(Dispatchers::class)
    }

    private fun createViewModel(listName: String = "Custom", listType: MediaType = MediaType.MOVIE) {
        val savedStateHandle = SavedStateHandle(mapOf(
            "listName" to listName,
            "listType" to listType.value,
            "listId" to 123
        ))
        
        coEvery { getMovieListDetailsUseCase(any(), any()) } returns emptyList()
        coEvery { getFavoriteUseCase() } returns emptyList()
        coEvery { getWatchlistUseCase() } returns emptyList()

        viewModel = ListContentsViewModel(
            stringsRes, getFavoriteUseCase, getWatchlistUseCase,
            getMovieListDetailsUseCase, deleteFavoriteUseCase,
            deleteMovieFromDetailsListUseCase, deleteWatchlistUseCase,
            listContentsUiMapper, savedStateHandle
        )
    }

    @Test
    fun `MovieClicked should emit NavigateToMovieContents effect`() = runTest {
        // Given
        createViewModel()
        val effects = mutableListOf<ListContentsUiEffect>()
        val job = launch { viewModel.effect.toList(effects) }
        advanceUntilIdle()

        // When
        viewModel.onEvent(ListContentsUiEvent.MovieClicked(100))
        advanceUntilIdle()

        // Then
        assertTrue(effects.any { it is ListContentsUiEffect.NavigateToMovieContents && it.movieId == 100 })
        job.cancel()
    }

    @Test
    fun `TvShowClicked should emit NavigateToTvShowContents effect`() = runTest {
        // Given
        createViewModel(listType = MediaType.TV_SHOW)
        val effects = mutableListOf<ListContentsUiEffect>()
        val job = launch { viewModel.effect.toList(effects) }
        advanceUntilIdle()

        // When
        viewModel.onEvent(ListContentsUiEvent.TvShowClicked(200))
        advanceUntilIdle()

        // Then
        assertTrue(effects.any { it is ListContentsUiEffect.NavigateToTvShowContents && it.tvShowId == 200 })
        job.cancel()
    }
    
    @Test
    fun `getData for Custom list should pass listType to usecase`() = runTest {
        // Given
        val listId = 123
        val listType = MediaType.TV_SHOW
        createViewModel(listName = "My TV List", listType = listType)
        
        // When
        advanceUntilIdle()

        // Then
        coVerify { getMovieListDetailsUseCase(listId, listType) }
    }
}
