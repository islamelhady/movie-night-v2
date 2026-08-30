package com.elhady.movies.feature.details.presentation.tvdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToUserListUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.CreateUserListUseCase
import com.elhady.movies.core.domain.usecase.account.GetUserListsUseCase
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetRatingTvUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsCastUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsInfoUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsReviewsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsSeasonsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowRecommendationsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowYoutubeDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.RateTvShowUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.CastUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsInfoToInfoUIStateMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsReviewUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsSeasonUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvShowToUIStateMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvShowYoutubeVideoDetailsUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.UserListsUiMapper
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TvDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: TvDetailsViewModel

    private val infoToInfoUIStateMapper = TvDetailsInfoToInfoUIStateMapper()
    private val tvShowToUIStateMapper = TvShowToUIStateMapper()
    private val castUiMapper = CastUiMapper()
    private val seasonUiMapper = TvDetailsSeasonUiMapper()
    private val reviewUiMapper = TvDetailsReviewUiMapper()
    private val tvDetailsInfoUseCase: GetTvDetailsInfoUseCase = mockk()
    private val getTvDetailsCastUseCase: GetTvDetailsCastUseCase = mockk()
    private val getTvDetailsSeasonsUseCase: GetTvDetailsSeasonsUseCase = mockk()
    private val rateTvShowUseCase: RateTvShowUseCase = mockk()
    private val getTvDetailsReviewsUseCase: GetTvDetailsReviewsUseCase = mockk()
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase = mockk()
    private val getTvShowYoutubeDetailsUseCase: GetTvShowYoutubeDetailsUseCase = mockk()
    private val getUserListsUseCase: GetUserListsUseCase = mockk()
    private val addToUserListUseCase: AddToUserListUseCase = mockk()
    private val createUserListUseCase: CreateUserListUseCase = mockk()
    private val deleteMovieFromDetailsListUseCase: com.elhady.movies.core.domain.usecase.account.DeleteMovieFromDetailsListUseCase = mockk()
    private val addToFavouriteUseCase: AddToFavouriteUseCase = mockk()
    private val addToWatchList: AddToWatchList = mockk()
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase = mockk()
    private val getRatingTvUseCase: GetRatingTvUseCase = mockk()
    private val tvShowYoutubeVideoDetailsUiMapper = TvShowYoutubeVideoDetailsUiMapper()
    private val userListsUiMapper = UserListsUiMapper()
    private val stringsRes: StringsRes = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { checkIsUserLoggedInUseCase() } returns true
        coEvery { getRatingTvUseCase(any()) } returns 4.0f
    }

    @After
    fun tearDown() {
    }

    private fun setupDefaultMocks(youtubeKey: String = "key") {
        coEvery { tvDetailsInfoUseCase(any()) } returns mockk<TvDetailsInfo>(relaxed = true)
        coEvery { getTvDetailsCastUseCase(any()) } returns emptyList()
        coEvery { getTvDetailsSeasonsUseCase(any()) } returns emptyList()
        coEvery { getTvDetailsReviewsUseCase(any()) } returns emptyList()
        coEvery { getTvShowRecommendationsUseCase(any()) } returns emptyList()
        coEvery { getUserListsUseCase(any(), any()) } returns emptyList()
        coEvery { getTvShowYoutubeDetailsUseCase(any()) } returns mockk<YoutubeVideoDetails> {
            every { key } returns youtubeKey
        }
    }

    private fun createViewModel(tvShowId: Int? = 1) {
        val savedStateHandle = if (tvShowId != null) {
            SavedStateHandle(mapOf("tvShowId" to tvShowId))
        } else {
            SavedStateHandle()
        }

        viewModel = TvDetailsViewModel(
            infoToInfoUIStateMapper, tvShowToUIStateMapper, castUiMapper,
            seasonUiMapper, reviewUiMapper, tvDetailsInfoUseCase,
            getTvDetailsCastUseCase, getTvDetailsSeasonsUseCase, rateTvShowUseCase,
            getTvDetailsReviewsUseCase, getTvShowRecommendationsUseCase,
            getTvShowYoutubeDetailsUseCase, getUserListsUseCase,
            addToUserListUseCase, createUserListUseCase, deleteMovieFromDetailsListUseCase,
            addToFavouriteUseCase, addToWatchList, checkIsUserLoggedInUseCase,
            getRatingTvUseCase, tvShowYoutubeVideoDetailsUiMapper, userListsUiMapper,
            stringsRes, savedStateHandle
        )
    }

    @Test
    fun `SaveClicked should initialize Favourite and Watchlist as selected`() = runTest {
        // Given
        setupDefaultMocks()
        coEvery { tvDetailsInfoUseCase(any()) } returns mockk<TvDetailsInfo>(relaxed = true) {
            every { accountStates?.favorite } returns true
            every { accountStates?.watchlist } returns true
        }
        createViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(TvDetailsUiEvent.SaveClicked)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.saveToListsUiState.isFavouriteSelected)
        assertTrue(viewModel.state.value.saveToListsUiState.isWatchlistSelected)
        coVerify { getUserListsUseCase(1, "tv") }
    }

    @Test
    fun `FavouriteClicked should toggle selection state in UI without calling API`() = runTest {
        // Given
        setupDefaultMocks()
        coEvery { tvDetailsInfoUseCase(any()) } returns mockk<TvDetailsInfo>(relaxed = true) {
            every { accountStates?.favorite } returns true
            every { accountStates?.watchlist } returns true
        }
        createViewModel()
        advanceUntilIdle()
        viewModel.onEvent(TvDetailsUiEvent.SaveClicked)
        advanceUntilIdle()

        // When
        viewModel.onEvent(TvDetailsUiEvent.FavouriteClicked)

        // Then
        assertFalse(viewModel.state.value.saveToListsUiState.isFavouriteSelected)
        coVerify(exactly = 0) { addToFavouriteUseCase(any(), any(), any()) }
    }

    @Test
    fun `DoneAddingLists should sync all selections and send tv mediaType`() = runTest {
        // Given
        setupDefaultMocks()
        coEvery { tvDetailsInfoUseCase(any()) } returns mockk<TvDetailsInfo>(relaxed = true) {
            every { accountStates?.favorite } returns true
            every { accountStates?.watchlist } returns false
        }
        coEvery { addToFavouriteUseCase(any(), "tv", false) } returns mockk()
        coEvery { addToWatchList(any(), "tv", true) } returns mockk()
        createViewModel(tvShowId = 600)
        advanceUntilIdle()
        
        viewModel.onEvent(TvDetailsUiEvent.SaveClicked) 
        advanceUntilIdle()
        viewModel.onEvent(TvDetailsUiEvent.FavouriteClicked) // Fav: T -> F
        viewModel.onEvent(TvDetailsUiEvent.WatchlistClicked) // Watch: F -> T
        advanceUntilIdle()

        // When
        viewModel.onEvent(TvDetailsUiEvent.DoneAddingLists)
        advanceUntilIdle()

        // Then
        coVerify { addToFavouriteUseCase(600, "tv", false) }
        coVerify { addToWatchList(600, "tv", true) }
    }

    @Test
    fun `Custom list selection changes should send ADD and REMOVE on Done for TV`() = runTest {
        // Given
        coEvery { tvDetailsInfoUseCase(any()) } returns mockk(relaxed = true)
        setupDefaultMocks()
        
        val userLists = listOf(
            com.elhady.movies.core.domain.model.account.UserList(id = 15, name = "List 15", isContainsMovie = true),
            com.elhady.movies.core.domain.model.account.UserList(id = 25, name = "List 25", isContainsMovie = false)
        )
        coEvery { getUserListsUseCase(any(), "tv") } returns userLists
        coEvery { addToUserListUseCase(any(), any(), any()) } returns mockk()
        coEvery { deleteMovieFromDetailsListUseCase(any(), any()) } returns mockk()

        createViewModel(tvShowId = 200)
        advanceUntilIdle()
        
        viewModel.onEvent(TvDetailsUiEvent.SaveClicked) // initial state: [15]
        advanceUntilIdle()
        
        viewModel.onEvent(TvDetailsUiEvent.ListSelected(15)) // remove 15
        viewModel.onEvent(TvDetailsUiEvent.ListSelected(25)) // add 25
        
        // When
        viewModel.onEvent(TvDetailsUiEvent.DoneAddingLists)
        advanceUntilIdle()
        
        // Then
        coVerify { deleteMovieFromDetailsListUseCase(15, 200) }
        coVerify { addToUserListUseCase(25, 200, "tv") }
    }
}
