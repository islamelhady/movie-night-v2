package com.elhady.movies.core.domain.usecase.common

import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.utils.Clock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class RefreshIfNeededUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var clock: Clock
    private lateinit var refreshIfNeededUseCase: RefreshIfNeededUseCase

    @Before
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        clock = mockk()
        refreshIfNeededUseCase = RefreshIfNeededUseCase(movieRepository, clock)
    }

    @Test
    fun `invoke should refresh when last refresh time is null`() = runTest {
        // Given
        coEvery { movieRepository.getLastRefreshTime() } returns null
        coEvery { clock.now() } returns 1000L

        // When
        val result = refreshIfNeededUseCase()

        // Then
        assertTrue(result)
        coVerify(exactly = 1) { movieRepository.refreshAll() }
        coVerify(exactly = 1) { movieRepository.setLastRefreshTime(1000L) }
    }

    @Test
    fun `invoke should not refresh when last refresh was less than 24 hours ago`() = runTest {
        // Given
        val now = 1000000L
        val lastRefresh = now - TimeUnit.HOURS.toMillis(23)
        coEvery { movieRepository.getLastRefreshTime() } returns lastRefresh
        coEvery { clock.now() } returns now

        // When
        val result = refreshIfNeededUseCase()

        // Then
        assertFalse(result)
        coVerify(exactly = 0) { movieRepository.refreshAll() }
    }

    @Test
    fun `invoke should refresh when last refresh was exactly 24 hours ago`() = runTest {
        // Given
        val now = 1000000L
        val lastRefresh = now - TimeUnit.HOURS.toMillis(24)
        coEvery { movieRepository.getLastRefreshTime() } returns lastRefresh
        coEvery { clock.now() } returns now

        // When
        val result = refreshIfNeededUseCase()

        // Then
        assertTrue(result)
        coVerify(exactly = 1) { movieRepository.refreshAll() }
    }

    @Test
    fun `invoke should only refresh once when called concurrently`() = runTest {
        // Given
        var lastRefreshTime: Long? = null
        coEvery { movieRepository.getLastRefreshTime() } answers { lastRefreshTime }
        coEvery { movieRepository.setLastRefreshTime(any()) } answers { lastRefreshTime = it.invocation.args[0] as Long }
        coEvery { clock.now() } returns 1000L
        
        // When
        val deferreds = List(5) {
            async { refreshIfNeededUseCase() }
        }
        val results = deferreds.awaitAll()

        // Then
        assertEquals(1, results.count { it })
        coVerify(exactly = 1) { movieRepository.refreshAll() }
    }

    @Test
    fun `invoke should not update last refresh time if refresh fails`() = runTest {
        // Given
        coEvery { movieRepository.getLastRefreshTime() } returns null
        coEvery { clock.now() } returns 1000L
        coEvery { movieRepository.refreshAll() } throws Exception("Network error")

        // When
        try {
            refreshIfNeededUseCase()
        } catch (e: Exception) {
            // Expected
        }

        // Then
        coVerify(exactly = 1) { movieRepository.refreshAll() }
        coVerify(exactly = 0) { movieRepository.setLastRefreshTime(any()) }
    }
}
