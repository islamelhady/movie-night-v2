package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.movie.GenreRemoteDto
import com.elhady.movies.core.domain.model.common.GenreEntity
import javax.inject.Inject

class DomainMainGenreMapper @Inject constructor() : Mapper<GenreRemoteDto, GenreEntity> {
    override fun map(input: GenreRemoteDto): GenreEntity {
        return GenreEntity(
            genreID =  input.id?:0,
            genreName = input.name?:"",
        )
    }
}
