package com.elhady.movies.feature.profile.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.model.auth.Profile
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.auth.GetAccountDetailsUseCase
import com.elhady.movies.core.domain.usecase.auth.LogoutUseCase
import com.elhady.movies.core.domain.usecase.common.GetThemeUseCase
import com.elhady.movies.core.domain.usecase.common.SaveThemeUseCase
import com.elhady.movies.feature.profile.presentation.profile.mapper.ProfileUiMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: ProfileViewModel

    private val getAccountDetailsUseCase: GetAccountDetailsUseCase = mockk()
    private val logoutUseCase: LogoutUseCase = mockk()
    private val profileUiMapper = ProfileUiMapper()
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase = mockk()
    private val getThemeUseCase: GetThemeUseCase = mockk()
    private val saveThemeUseCase: SaveThemeUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        
        // Default mocks
        every { getThemeUseCase() } returns false
        every { checkIsUserLoggedInUseCase() } returns false
    }

    @org.junit.After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    private fun initViewModel() {
        viewModel = ProfileViewModel(
            getAccountDetailsUseCase,
            logoutUseCase,
            profileUiMapper,
            checkIsUserLoggedInUseCase,
            getThemeUseCase,
            saveThemeUseCase
        )
    }

    @Test
    fun `initial state should load theme correctly`() = runTest {
        // Given
        every { getThemeUseCase() } returns true

        // When
        initViewModel()

        // Then
        assertEquals(true, viewModel.state.value.isDarkTheme)
    }

    @Test
    fun `checkUserLoggedIn should fetch account details when logged in`() = runTest {
        // Given
        every { checkIsUserLoggedInUseCase() } returns true
        val profile = Profile(username = "elhady", avatarUrl = "url")
        coEvery { getAccountDetailsUseCase() } returns profile

        // When
        initViewModel()

        // Then
        assertEquals(true, viewModel.state.value.isLogIn)
        assertEquals("elhady", viewModel.state.value.username)
        assertEquals("url", viewModel.state.value.avatarUrl)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `checkUserLoggedIn should update state when not logged in`() = runTest {
        // Given
        every { checkIsUserLoggedInUseCase() } returns false

        // When
        initViewModel()

        // Then
        assertEquals(false, viewModel.state.value.isLogIn)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `logout should update state and emit NavigateToLogin effect`() = runTest {
        // Given
        every { checkIsUserLoggedInUseCase() } returns true
        coEvery { getAccountDetailsUseCase() } returns Profile("user", "url")
        coEvery { logoutUseCase() } returns Unit
        initViewModel()

        val effects = mutableListOf<ProfileUiEffect>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect { effects.add(it) }
        }

        // When
        viewModel.onEvent(ProfileUiEvent.LogoutConfirmed)

        // Then
        assertEquals(false, viewModel.state.value.isLogIn)
        assertTrue(effects.contains(ProfileUiEffect.NavigateToLogin))

        collectJob.cancel()
    }

    @Test
    fun `ThemeChanged event should update state and call usecase`() = runTest {
        // Given
        initViewModel()
        coEvery { saveThemeUseCase(any()) } returns Unit

        // When
        viewModel.onEvent(ProfileUiEvent.ThemeChanged(true))

        // Then
        assertEquals(true, viewModel.state.value.isDarkTheme)
        coVerify { saveThemeUseCase(true) }
    }

    @Test
    fun `onAccountDetailsError with Unauthorized should trigger logout`() = runTest {
        // Given
        every { checkIsUserLoggedInUseCase() } returns true
        coEvery { getAccountDetailsUseCase() } throws AppException.Unauthorized
        coEvery { logoutUseCase() } returns Unit

        // When
        initViewModel()

        // Then
        coVerify { logoutUseCase() }
        assertEquals(false, viewModel.state.value.isLogIn)
    }
}
