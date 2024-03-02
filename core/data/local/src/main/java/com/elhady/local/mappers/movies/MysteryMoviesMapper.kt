package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.MysteryMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
import javax.inject.Inject

class MysteryMoviesMapper @Inject constructor() : Mapper<MovieDto, MysteryMovieLocalDto> {
    override fun map(input: MovieDto): MysteryMovieLocalDto {
        return MysteryMovieLocalDto(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}