package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.network.dto.movie.MovieDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDtoMapperTest {

    private val mapper = MovieDtoMapper()

    @Test
    fun `map DTO to Movie should preserve movie mediaType when specified`() {
        // Given
        val dto = MovieDto(
            id = 123,
            title = "Inception",
            voteAverage = 8.8,
            posterPath = "/path.jpg",
            releaseDate = "2010-07-16",
            adult = false,
            backdropPath = null,
            genreIds = emptyList(),
            originalLanguage = "en",
            originalTitle = "Inception",
            overview = "Dreams",
            popularity = 100.0,
            video = false,
            voteCount = 1000
        )

        // When
        val result = mapper.map(dto, emptyList(), mediaType = "movie")

        // Then
        assertEquals("movie", result.mediaType)
    }

    @Test
    fun `map DTO to Movie should preserve tv mediaType when specified`() {
        // Given
        val dto = MovieDto(
            id = 456,
            title = "Breaking Bad",
            voteAverage = 9.5,
            posterPath = "/path2.jpg",
            releaseDate = "2008-01-20",
            adult = false,
            backdropPath = null,
            genreIds = emptyList(),
            originalLanguage = "en",
            originalTitle = "Breaking Bad",
            overview = "Chemistry",
            popularity = 200.0,
            video = false,
            voteCount = 2000
        )

        // When
        val result = mapper.map(dto, emptyList(), mediaType = "tv")

        // Then
        assertEquals("tv", result.mediaType)
    }
}
