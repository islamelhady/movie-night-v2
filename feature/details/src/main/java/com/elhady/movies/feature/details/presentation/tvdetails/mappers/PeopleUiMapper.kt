package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.people.PeopleEntity
import com.elhady.movies.core.ui.model.PeopleUIState
import javax.inject.Inject

class PeopleUiMapper @Inject constructor() :
    Mapper<PeopleEntity, PeopleUIState> {
    override fun map(input: PeopleEntity): PeopleUIState {
        return PeopleUIState(
            input.id,
            input.name,
            input.imageUrl
        )
    }
}
