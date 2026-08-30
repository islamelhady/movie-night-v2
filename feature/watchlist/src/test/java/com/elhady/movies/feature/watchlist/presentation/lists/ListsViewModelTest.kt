package com.elhady.movies.feature.watchlist.presentation.lists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.account.CreateListUseCase
import com.elhady.movies.core.domain.usecase.account.DeleteListUseCase
import com.elhady.movies.core.domain.usecase.account.GetListsCreatedUseCase
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.watchlist.presentation.lists.mapper.ListsUiMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ListsViewModel

    private val getListsCreatedUseCase: GetListsCreatedUseCase = mockk()
    private val deleteListUseCase: DeleteListUseCase = mockk()
    private val createListUseCase: CreateListUseCase = mockk()
    private val listsUiMapper = ListsUiMapper()
    private val stringsRes: StringsRes = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getListsCreatedUseCase() } returns emptyList()
    }

    private fun initViewModel() {
        viewModel = ListsViewModel(
            getListsCreatedUseCase,
            deleteListUseCase,
            createListUseCase,
            listsUiMapper,
            stringsRes
        )
    }

    @Test
    fun `Initial state should have isLoading as true`() = runTest {
        // Given
        coEvery { getListsCreatedUseCase() } coAnswers {
            kotlinx.coroutines.delay(1000)
            emptyList()
        }

        // When
        initViewModel()

        // Then
        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `getData success should set isLoading to false`() = runTest {
        // Given
        initViewModel()

        // When
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `getData error should set isLoading to false and set error`() = runTest {
        // Given
        coEvery { getListsCreatedUseCase() } throws AppException.NoNetwork
        
        // When
        initViewModel()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.isFailure)
    }

    @Test
    fun `CreateList should call usecase and show success SnackBar`() = runTest {
        // Given
        val listName = "My New List"
        coEvery { createListUseCase(any()) } returns true
        every { stringsRes.newListAddSuccessFully } returns "Success"
        
        initViewModel()
        
        val effects = mutableListOf<ListsUiEffect>()
        val job = launch {
            viewModel.effect.toList(effects)
        }
        
        advanceUntilIdle()

        // When
        viewModel.onEvent(ListsUiEvent.CreateList(listName))
        advanceUntilIdle()

        // Then
        coVerify { createListUseCase(listName) }
        assertTrue(effects.any { it is ListsUiEffect.ShowSnackBar && it.message == "Success" })
        
        job.cancel()
    }

    @Test
    fun `onError should resolve localized message from stringsRes`() = runTest {
        // Given
        coEvery { getListsCreatedUseCase() } throws AppException.NoNetwork
        every { stringsRes.noNetworkConnection } returns "No Internet"
        
        val effects = mutableListOf<ListsUiEffect>()
        
        // When
        initViewModel()
        val job = launch {
            viewModel.effect.toList(effects)
        }
        advanceUntilIdle()

        // Then
        assertTrue(effects.any { it is ListsUiEffect.ShowSnackBar && it.message == "No Internet" })
        
        job.cancel()
    }
}
