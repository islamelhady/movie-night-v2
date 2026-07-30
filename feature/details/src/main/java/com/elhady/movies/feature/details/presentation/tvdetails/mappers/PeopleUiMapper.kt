package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.ui.state.PeopleUIState
import javax.inject.Inject

class PeopleUiMapper @Inject constructor() :
    Mapper<People, PeopleUIState> {
    override fun map(input: People): PeopleUIState {
        return PeopleUIState(
            input.id,
            input.name,
            input.imageUrl
        )
    }
}
