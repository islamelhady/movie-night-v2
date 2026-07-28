package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.CastEntity
import com.elhady.movies.core.ui.model.PeopleUIState
import javax.inject.Inject

class CastUiMapper @Inject constructor() :
    Mapper<CastEntity, PeopleUIState> {
    override fun map(input: CastEntity): PeopleUIState {
        return PeopleUIState(
            id = input.id,
            name = input.name,
            imageUrl = input.profilePath
        )
    }
}
