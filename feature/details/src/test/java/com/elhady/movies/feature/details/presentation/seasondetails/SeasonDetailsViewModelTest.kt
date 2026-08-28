package com.elhady.movies.feature.details.presentation.seasondetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.domain.usecase.tvshow.GetSeasonDetailsUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.seasondetails.mapper.SeasonDetailsUiMapper
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
class SeasonDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SeasonDetailsViewModel

    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase = mockk()
    private val seasonDetailsUiMapper = SeasonDetailsUiMapper()
    private val stringsRes: StringsRes = mockk(relaxed = true)

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

    private fun createViewModel(seriesId: Int? = 1, seasonNumber: Int? = 1) {
        val args = mutableMapOf<String, Any>()
        if (seriesId != null) args["seriesId"] = seriesId
        if (seasonNumber != null) args["seasonNumber"] = seasonNumber
        
        viewModel = SeasonDetailsViewModel(
            getSeasonDetailsUseCase, seasonDetailsUiMapper, stringsRes, SavedStateHandle(args)
        )
    }

    @Test
    fun `init with valid args should load data successfully`() = runTest {
        // Given
        coEvery { getSeasonDetailsUseCase(any(), any()) } returns mockk(relaxed = true)

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
}
