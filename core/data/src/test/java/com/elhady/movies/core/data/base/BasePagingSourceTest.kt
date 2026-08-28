package com.elhady.movies.core.data.base

import androidx.paging.PagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class BasePagingSourceTest {

    private class FakePagingSource(
        private val data: List<Int>
    ) : BasePagingSource<Unit, Int>() {
        override suspend fun fetchData(page: Int): List<Int> = data
    }

    @Test
    fun `load should return nextKey when data is not empty`() = runTest {
        // Given
        val pagingSource = FakePagingSource(listOf(1, 2, 3))
        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 3,
            placeholdersEnabled = false
        )

        // When
        val result = pagingSource.load(params) as PagingSource.LoadResult.Page

        // Then
        assertEquals(2, result.nextKey)
    }

    @Test
    fun `load should return null nextKey when data is empty`() = runTest {
        // Given
        val pagingSource = FakePagingSource(emptyList())
        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 3,
            placeholdersEnabled = false
        )

        // When
        val result = pagingSource.load(params) as PagingSource.LoadResult.Page

        // Then
        assertEquals(null, result.nextKey)
    }
}
