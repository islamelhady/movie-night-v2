package com.elhady.movies.core.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MediaTypeTest {

    @Test
    fun `toMediaType should return MOVIE when input is movie`() {
        val result = "movie".toMediaType()
        assertEquals(MediaType.MOVIE, result)
    }

    @Test
    fun `toMediaType should return TV_SHOW when input is tv`() {
        val result = "tv".toMediaType()
        assertEquals(MediaType.TV_SHOW, result)
    }

    @Test
    fun `toMediaType should return null when input is unknown`() {
        val result = "unknown".toMediaType()
        assertNull(result)
    }

    @Test
    fun `toMediaType should return null when input is null`() {
        val input: String? = null
        val result = input.toMediaType()
        assertNull(result)
    }

    @Test
    fun `toMediaType should return null when input is empty`() {
        val result = "".toMediaType()
        assertNull(result)
    }
}
