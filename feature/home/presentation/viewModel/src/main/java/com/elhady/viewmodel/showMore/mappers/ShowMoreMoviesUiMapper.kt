package com.elhady.viewmodel.showMore.mappers

import com.elhady.entities.GenreEntity
import com.elhady.entities.MovieEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.showMore.ShowMoreUi
import javax.inject.Inject

class ShowMoreMoviesUiMapper @Inject constructor() :
    Mapper<MovieEntity, ShowMoreUi> {
    override fun map(input: MovieEntity): ShowMoreUi {
        return ShowMoreUi(
            id = input.movieId,
            name = input.movieName,
            imageUrl = input.movieImage,
            year = input.movieYear,
            genreEntities = convertGenreListToString(input.movieGenreEntities),
            rate = input.movieRate,
        )
    }

    private fun convertGenreListToString(input: List<GenreEntity>): String {
        return input.joinToString(" | ") { it.genreName }
    }

}