package com.elhady.movies.feature.details.presentation.peopledetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.people.GetMoviesByPersonUseCase
import com.elhady.movies.core.domain.usecase.people.GetPeopleDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowsByPersonUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.peopledetails.mapper.MoviesByPeopleUiMapper
import com.elhady.movies.feature.details.presentation.peopledetails.mapper.PeopleDataUiMapper
import com.elhady.movies.feature.details.presentation.peopledetails.mapper.TvShowsByPeopleUiMapper
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
class PeopleDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PeopleDetailsViewModel

    private val getPeopleDetailsUseCase: GetPeopleDetailsUseCase = mockk()
    private val getMoviesByPersonUseCase: GetMoviesByPersonUseCase = mockk()
    private val getTvShowsByPersonUseCase: GetTvShowsByPersonUseCase = mockk()
    private val peopleDataUiMapper = PeopleDataUiMapper()
    private val moviesByPeopleUiMapper = MoviesByPeopleUiMapper()
    private val tvShowsByPeopleUiMapper = TvShowsByPeopleUiMapper()
    private val stringsRes: StringsRes = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher

        every { stringsRes.someThingError } returns "Error"
    }

    @After
    fun tearDown() {
        unmockkStatic(Dispatchers::class)
    }

    private fun setupDefaultMocks() {
        coEvery { getPeopleDetailsUseCase(any()) } returns mockk(relaxed = true)
        coEvery { getMoviesByPersonUseCase(any()) } returns emptyList()
        coEvery { getTvShowsByPersonUseCase(any()) } returns emptyList()
    }

    private fun createViewModel(personId: Int? = 1) {
        val savedStateHandle = if (personId != null) {
            SavedStateHandle(mapOf("personId" to personId))
        } else {
            SavedStateHandle()
        }
        
        viewModel = PeopleDetailsViewModel(
            getPeopleDetailsUseCase, getMoviesByPersonUseCase, getTvShowsByPersonUseCase,
            peopleDataUiMapper, moviesByPeopleUiMapper, tvShowsByPeopleUiMapper,
            stringsRes, savedStateHandle
        )
    }

    @Test
    fun `init with valid personId should load data successfully`() = runTest {
        // Given
        setupDefaultMocks()

        // When
        createViewModel(personId = 1)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.error == null)
    }

    @Test
    fun `init with missing personId should NOT crash and show error`() = runTest {
        // When
        createViewModel(personId = null)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.isFailure)
    }

    @Test
    fun `refreshScreen failure on one request should show aggregated error`() = runTest {
        // Given
        coEvery { getPeopleDetailsUseCase(any()) } throws AppException.NoNetwork
        coEvery { getMoviesByPersonUseCase(any()) } returns emptyList()
        coEvery { getTvShowsByPersonUseCase(any()) } returns emptyList()

        // When
        createViewModel()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.isFailure)
        assertFalse(viewModel.state.value.isPersonLoading)
    }
}
