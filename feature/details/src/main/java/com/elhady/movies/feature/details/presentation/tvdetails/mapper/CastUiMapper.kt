package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.ui.state.PeopleUiState
import javax.inject.Inject

class CastUiMapper @Inject constructor() :
    Mapper<People, PeopleUiState> {
    override fun map(input: People): PeopleUiState {
        return PeopleUiState(
            input.id,
            input.name,
            input.imageUrl
        )
    }
}
