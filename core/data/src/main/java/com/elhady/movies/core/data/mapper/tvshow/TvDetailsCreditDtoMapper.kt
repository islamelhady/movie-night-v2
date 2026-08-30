package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.tvshow.TvDetailsCreditDto
import com.elhady.movies.core.domain.model.people.People
import javax.inject.Inject

class TvDetailsCreditDtoMapper @Inject constructor() :
    Mapper<TvDetailsCreditDto, List<People>> {
    override fun map(input: TvDetailsCreditDto): List<People> {
        return mapCastToEntity(input.cast)
    }

    private fun mapCastToEntity(castDto: List<TvDetailsCreditDto.CastDto?>?)
            : List<People> {
        return castDto?.map { actor ->
            People(
                id = actor?.id ?: 0,
                name = actor?.name ?: "",
                imageUrl = (BuildConfig.IMAGE_BASE_PATH + actor?.profilePath) ?: ""
            )
        } ?: emptyList()
    }

}
