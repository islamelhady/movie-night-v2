package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.movie.GenreDto
import com.elhady.movies.core.domain.model.common.Genre
import javax.inject.Inject

class DomainMainGenreMapper @Inject constructor() : Mapper<GenreDto, Genre> {
    override fun map(input: GenreDto): Genre {
        return Genre(
            genreID =  input.id?:0,
            genreName = input.name?:"",
        )
    }
}
