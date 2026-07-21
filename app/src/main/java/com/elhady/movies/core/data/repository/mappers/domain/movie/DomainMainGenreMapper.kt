package com.elhady.movies.core.data.repository.mappers.domain.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.moviedetails.GenreRemoteDto
import com.elhady.movies.core.domain.entities.GenreEntity
import javax.inject.Inject

class DomainMainGenreMapper @Inject constructor() : Mapper<GenreRemoteDto, GenreEntity> {
    override fun map(input: GenreRemoteDto): GenreEntity {
        return GenreEntity(
            genreID =  input.id?:0,
            genreName = input.name?:"",
        )
    }
}
