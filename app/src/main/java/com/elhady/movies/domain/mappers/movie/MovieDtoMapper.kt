package com.elhady.movies.domain.mappers.movie

import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class MovieDtoMapper @Inject constructor():Mapper<MovieDto, Media> {
    override fun map(input: MovieDto): Media {
        return Media(
            mediaID = input.id ?: 0,
            mediaName = input.title ?: "",
            mediaImage = (Constants.IMAGE_PATH + input.posterPath),
            mediaType = MediaType.MOVIES.value,
            mediaRate = input.voteAverage?.toFloat() ?: 0f,
            mediaDate = input.releaseDate?.substringBefore('-') ?: ""
        )
    }
}