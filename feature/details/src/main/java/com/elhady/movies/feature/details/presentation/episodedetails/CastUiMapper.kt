package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.PeopleEntity
import com.elhady.movies.core.common.presentation.model.PeopleUIState
import javax.inject.Inject

class CastUiMapper @Inject constructor() : Mapper<PeopleEntity, PeopleUIState> {
    override fun map(input: PeopleEntity): PeopleUIState {
        return PeopleUIState(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl
        )
    }
}
