package com.elhady.movies.feature.details.presentation.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToUserListUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.CreateUserListUseCase
import com.elhady.movies.core.domain.usecase.account.GetUserListsUseCase
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.movie.GetMovieDetailsUseCase
import com.elhady.movies.core.domain.usecase.movie.GetRatingMovieUseCase
import com.elhady.movies.core.domain.usecase.movie.InsertMovieToWatchHistoryUseCase
import com.elhady.movies.core.domain.usecase.movie.SetRatingUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.CastUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.RecommendedUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.ReviewDetailsUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.ReviewsUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.UpperUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.UserListUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.WatchHistoryUiMapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: MovieDetailsViewModel

    private val movieDetailsUseCase: GetMovieDetailsUseCase = mockk()
    private val ratingUseCase: SetRatingUseCase = mockk()
    private val getUserListsUseCase: GetUserListsUseCase = mockk()
    private val addToUserListUseCase: AddToUserListUseCase = mockk()
    private val createUserListUseCase: CreateUserListUseCase = mockk()
    private val addToFavouriteUseCase: AddToFavouriteUseCase = mockk()
    private val addToWatchList: AddToWatchList = mockk()
    private val insertMovieToWatchHistoryUseCase: InsertMovieToWatchHistoryUseCase = mockk(relaxed = true)
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase = mockk()
    private val getRatingMovieUseCase: GetRatingMovieUseCase = mockk()
    private val stringsRes: StringsRes = mockk(relaxed = true)

    private val recommendedUiMapper = RecommendedUiMapper()
    private val upperUiMapper = UpperUiMapper()
    private val reviewsUiMapper = ReviewsUiMapper()
    private val castUiMapper = CastUiMapper()
    private val reviewDetailsUiMapper = ReviewDetailsUiMapper()
    private val watchHistoryUiMapper = WatchHistoryUiMapper()
    private val userListUiMapper = UserListUiMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        
        every { checkIsUserLoggedInUseCase() } returns true
        coEvery { getRatingMovieUseCase(any()) } returns 4.5f
        every { stringsRes.someThingError } returns "Error"
    }

    @After
    fun tearDown() {
        unmockkStatic(Dispatchers::class)
    }

    private fun createViewModel(movieId: Int? = 1) {
        val savedStateHandle = if (movieId != null) {
            SavedStateHandle(mapOf("movieId" to movieId))
        } else {
            SavedStateHandle()
        }
        
        viewModel = MovieDetailsViewModel(
            movieDetailsUseCase, ratingUseCase, getUserListsUseCase,
            addToUserListUseCase, createUserListUseCase, addToFavouriteUseCase,
            addToWatchList, insertMovieToWatchHistoryUseCase, checkIsUserLoggedInUseCase,
            recommendedUiMapper, upperUiMapper, reviewsUiMapper, castUiMapper,
            reviewDetailsUiMapper, watchHistoryUiMapper, userListUiMapper,
            getRatingMovieUseCase, stringsRes, savedStateHandle
        )
    }

    @Test
    fun `init with valid movieId should load data successfully`() = runTest {
        // Given
        val movieDetails = mockk<MovieDetails>(relaxed = true)
        coEvery { movieDetailsUseCase(any()) } returns movieDetails

        // When
        createViewModel(movieId = 1)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.onErrors.isEmpty())
    }

    @Test
    fun `init with missing movieId should show error`() = runTest {
        // When
        createViewModel(movieId = null)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.onErrors.isNotEmpty())
        assertEquals("Error", viewModel.state.value.onErrors.first())
    }

    @Test
    fun `onEvent PlayClicked should set isPlayerVisible to true`() = runTest {
        // Given
        coEvery { movieDetailsUseCase(any()) } returns mockk(relaxed = true)
        createViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(MovieDetailsUiEvent.PlayClicked)

        // Then
        assertTrue(viewModel.state.value.isPlayerVisible)
    }

    @Test
    fun `onEvent DismissPlayerClicked should set isPlayerVisible to false`() = runTest {
        // Given
        coEvery { movieDetailsUseCase(any()) } returns mockk(relaxed = true)
        createViewModel()
        advanceUntilIdle()
        viewModel.onEvent(MovieDetailsUiEvent.PlayClicked)

        // When
        viewModel.onEvent(MovieDetailsUiEvent.DismissPlayerClicked)

        // Then
        assertFalse(viewModel.state.value.isPlayerVisible)
    }

    @Test
    fun `getMovieDetails failure should update state with errors`() = runTest {
        // Given
        coEvery { movieDetailsUseCase(any()) } throws AppException.NoNetwork

        // When
        createViewModel()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.onErrors.isNotEmpty())
    }
}
