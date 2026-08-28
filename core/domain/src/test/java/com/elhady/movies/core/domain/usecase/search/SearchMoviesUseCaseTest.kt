package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchMoviesUseCaseTest {

    private lateinit var useCase: SearchMoviesUseCase
    private val repository: SearchRepository = mockk()

    @Before
    fun setUp() {
        useCase = SearchMoviesUseCase(repository)
    }

    @Test
    fun `invoke should filter out movies with rate 0`() = runTest {
        // Given
        val movies = listOf(
            mockk<Movie> {
                coEvery { rate } returns 0.0
                coEvery { genreEntities } returns emptyList()
            },
            mockk<Movie> {
                coEvery { rate } returns 8.0
                coEvery { genreEntities } returns emptyList()
            }
        )
        coEvery { repository.searchForMovies(any()) } returns movies

        // When
        val result = useCase("Batman")

        // Then
        assertEquals(1, result.size)
        assertEquals(8.0, result[0].rate, 0.0)
    }

    @Test
    fun `invoke should filter by genreId when provided`() = runTest {
        // Given
        val genreId = 28
        val movies = listOf(
            mockk<Movie> {
                coEvery { rate } returns 8.0
                coEvery { genreEntities } returns listOf(Genre(genreID = 28, genreName = "Action"))
            },
            mockk<Movie> {
                coEvery { rate } returns 7.0
                coEvery { genreEntities } returns listOf(Genre(genreID = 12, genreName = "Adventure"))
            }
        )
        coEvery { repository.searchForMovies(any()) } returns movies

        // When
        val result = useCase("Batman", genreId)

        // Then
        assertEquals(1, result.size)
        assertEquals(28, result[0].genreEntities[0].genreID)
    }

    @Test
    fun `invoke should sort by rate descending`() = runTest {
        // Given
        val movies = listOf(
            mockk<Movie> {
                coEvery { rate } returns 7.0
                coEvery { genreEntities } returns emptyList()
            },
            mockk<Movie> {
                coEvery { rate } returns 9.0
                coEvery { genreEntities } returns emptyList()
            }
        )
        coEvery { repository.searchForMovies(any()) } returns movies

        // When
        val result = useCase("Batman")

        // Then
        assertEquals(9.0, result[0].rate, 0.0)
        assertEquals(7.0, result[1].rate, 0.0)
    }
}
