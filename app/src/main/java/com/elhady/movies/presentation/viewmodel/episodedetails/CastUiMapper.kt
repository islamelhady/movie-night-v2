package com.elhady.movies.presentation.viewmodel.episodedetails

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.PeopleEntity
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
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