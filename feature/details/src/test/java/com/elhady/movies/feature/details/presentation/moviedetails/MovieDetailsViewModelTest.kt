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
import io.mockk.coVerify
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
    private val deleteMovieFromDetailsListUseCase: com.elhady.movies.core.domain.usecase.account.DeleteMovieFromDetailsListUseCase = mockk()
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
        
        every { checkIsUserLoggedInUseCase() } returns true
        coEvery { getRatingMovieUseCase(any()) } returns 4.5f
        coEvery { getUserListsUseCase(any(), any()) } returns emptyList()
        every { stringsRes.someThingError } returns "Error"
    }

    @After
    fun tearDown() {
    }

    private fun createViewModel(movieId: Int? = 1) {
        val savedStateHandle = if (movieId != null) {
            SavedStateHandle(mapOf("movieId" to movieId))
        } else {
            SavedStateHandle()
        }
        
        viewModel = MovieDetailsViewModel(
            movieDetailsUseCase, ratingUseCase, getUserListsUseCase,
            addToUserListUseCase, createUserListUseCase, deleteMovieFromDetailsListUseCase,
            addToFavouriteUseCase, addToWatchList, insertMovieToWatchHistoryUseCase,
            checkIsUserLoggedInUseCase, recommendedUiMapper, upperUiMapper,
            reviewsUiMapper, castUiMapper, reviewDetailsUiMapper, watchHistoryUiMapper,
            userListUiMapper, getRatingMovieUseCase, stringsRes, savedStateHandle
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
    fun `SaveClicked should initialize Favourite and Watchlist as selected`() = runTest {
        // Given
        val movieDetails = mockk<MovieDetails>(relaxed = true) {
            every { accountStates?.favorite } returns true
            every { accountStates?.watchlist } returns true
        }
        coEvery { movieDetailsUseCase(any()) } returns movieDetails
        createViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(MovieDetailsUiEvent.SaveClicked)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.saveToListsUiState.isFavouriteSelected)
        assertTrue(viewModel.state.value.saveToListsUiState.isWatchlistSelected)
        coVerify { getUserListsUseCase(1, "movie") }
    }

    @Test
    fun `FavouriteClicked should toggle selection state in UI without calling API`() = runTest {
        // Given
        val movieDetails = mockk<MovieDetails>(relaxed = true) {
            every { accountStates?.favorite } returns true
            every { accountStates?.watchlist } returns true
        }
        coEvery { movieDetailsUseCase(any()) } returns movieDetails
        createViewModel()
        advanceUntilIdle()
        viewModel.onEvent(MovieDetailsUiEvent.SaveClicked) 
        advanceUntilIdle()

        // When
        viewModel.onEvent(MovieDetailsUiEvent.FavouriteClicked)

        // Then
        assertFalse(viewModel.state.value.saveToListsUiState.isFavouriteSelected)
        coVerify(exactly = 0) { addToFavouriteUseCase(any(), any(), any()) }
    }

    @Test
    fun `DoneClicked should sync all selections and send movie mediaType`() = runTest {
        // Given
        val movieDetails = mockk<MovieDetails>(relaxed = true) {
            every { accountStates?.favorite } returns false
            every { accountStates?.watchlist } returns true
        }
        coEvery { movieDetailsUseCase(any()) } returns movieDetails
        coEvery { addToFavouriteUseCase(any(), "movie", true) } returns mockk()
        coEvery { addToWatchList(any(), "movie", false) } returns mockk()
        createViewModel(movieId = 500)
        advanceUntilIdle()
        
        viewModel.onEvent(MovieDetailsUiEvent.SaveClicked) 
        advanceUntilIdle()
        viewModel.onEvent(MovieDetailsUiEvent.FavouriteClicked) // Fav: F -> T
        viewModel.onEvent(MovieDetailsUiEvent.WatchlistClicked) // Watch: T -> F
        advanceUntilIdle()

        // When
        viewModel.onEvent(MovieDetailsUiEvent.DoneClicked)
        advanceUntilIdle()

        // Then
        coVerify { addToFavouriteUseCase(500, "movie", true) }
        coVerify { addToWatchList(500, "movie", false) }
    }

    @Test
    fun `Custom list selection changes should send ADD and REMOVE on Done`() = runTest {
        // Given
        val movieDetails = mockk<MovieDetails>(relaxed = true)
        coEvery { movieDetailsUseCase(any()) } returns movieDetails
        
        val userLists = listOf(
            com.elhady.movies.core.domain.model.account.UserList(id = 10, name = "List 10", isContainsMovie = true),
            com.elhady.movies.core.domain.model.account.UserList(id = 20, name = "List 20", isContainsMovie = false)
        )
        coEvery { getUserListsUseCase(any(), "movie") } returns userLists
        coEvery { addToUserListUseCase(any(), any(), any()) } returns mockk()
        coEvery { deleteMovieFromDetailsListUseCase(any(), any()) } returns mockk()

        createViewModel(movieId = 100)
        advanceUntilIdle()
        
        viewModel.onEvent(MovieDetailsUiEvent.SaveClicked) // initial state: [10]
        advanceUntilIdle()
        
        viewModel.onEvent(MovieDetailsUiEvent.ChipClicked(10)) // remove 10
        viewModel.onEvent(MovieDetailsUiEvent.ChipClicked(20)) // add 20
        
        // When
        viewModel.onEvent(MovieDetailsUiEvent.DoneClicked)
        advanceUntilIdle()
        
        // Then
        coVerify { deleteMovieFromDetailsListUseCase(10, 100) }
        coVerify { addToUserListUseCase(20, 100, "movie") }
    }
}
