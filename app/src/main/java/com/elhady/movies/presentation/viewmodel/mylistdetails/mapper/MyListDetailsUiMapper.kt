package com.elhady.movies.presentation.viewmodel.mylistdetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.mylistdetails.MovieUiState
import javax.inject.Inject

class MyListDetailsUiMapper @Inject constructor() : Mapper<MovieEntity, MovieUiState> {
    override fun map(input: MovieEntity): MovieUiState {
        return MovieUiState(
            id= input.id,
            title = input.title,
            imageUrl =input.imageUrl,
            genres = input.convertGenreListToString(),
            year = input.extractYearFromDate(),
            rating = input.rate,
            mediaType =input.mediaType,
        )
    }

    private fun MovieEntity.convertGenreListToString(): String {
        return genreEntities.joinToString(" | ") { it.genreName }
    }

    private fun MovieEntity.extractYearFromDate(): String {
        val parts = year.split("-")
        return parts[0]
    }
}