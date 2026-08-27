package com.elhady.movies.feature.details.presentation.episodedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetCastForEpisodeUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetEpisodeDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetEpisodeVideoUseCase
import com.elhady.movies.core.domain.usecase.tvshow.SetEpisodeRatingUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.episodedetails.mapper.EpisodeDetailsUiMapper
import com.elhady.movies.feature.details.presentation.episodedetails.mapper.TrailerUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.CastUiMapper
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EpisodeDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: EpisodeDetailsViewModel

    private val setEpisodeRatingUseCase: SetEpisodeRatingUseCase = mockk()
    private val episodeDetailsUseCase: GetEpisodeDetailsUseCase = mockk()
    private val episodeDetailsUiMapper = EpisodeDetailsUiMapper()
    private val castUseCase: GetCastForEpisodeUseCase = mockk()
    private val castUiMapper = CastUiMapper()
    private val trailerUiMapper = TrailerUiMapper()
    private val episodeVideoUseCase: GetEpisodeVideoUseCase = mockk()
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase = mockk()
    private val stringsRes: StringsRes = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        
        every { checkIsUserLoggedInUseCase() } returns true
    }

    @After
    fun tearDown() {
        unmockkStatic(Dispatchers::class)
    }

    private fun setupDefaultMocks() {
        coEvery { episodeDetailsUseCase(any(), any(), any()) } returns mockk(relaxed = true)
        coEvery { castUseCase(any(), any(), any()) } returns emptyList()
        coEvery { episodeVideoUseCase(any(), any(), any()) } returns mockk(relaxed = true)
    }

    private fun createViewModel(seriesId: Int? = 1, seasonNumber: Int? = 1, episodeNumber: Int? = 1) {
        val args = mutableMapOf<String, Any>()
        if (seriesId != null) args["seriesId"] = seriesId
        if (seasonNumber != null) args["seasonNumber"] = seasonNumber
        if (episodeNumber != null) args["episodeNumber"] = episodeNumber
        
        viewModel = EpisodeDetailsViewModel(
            setEpisodeRatingUseCase, episodeDetailsUseCase, episodeDetailsUiMapper,
            castUseCase, castUiMapper, trailerUiMapper, episodeVideoUseCase,
            checkIsUserLoggedInUseCase, SavedStateHandle(args), stringsRes
        )
    }

    @Test
    fun `init with valid args should load data successfully`() = runTest {
        // Given
        setupDefaultMocks()

        // When
        createViewModel()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.error == null)
    }

    @Test
    fun `init with missing args should show error state`() = runTest {
        // When
        createViewModel(seriesId = null)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.isFailure)
    }

    @Test
    fun `onEvent PlayFullScreenClicked should set isPlayerVisible to true`() = runTest {
        // Given
        setupDefaultMocks()
        createViewModel()
        advanceUntilIdle()

        // When
        viewModel.onEvent(EpisodeDetailsUiEvent.PlayFullScreenClicked("key"))

        // Then
        assertTrue(viewModel.state.value.isPlayerVisible)
    }
}
