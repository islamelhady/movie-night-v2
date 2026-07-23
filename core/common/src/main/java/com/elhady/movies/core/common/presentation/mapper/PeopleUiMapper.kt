package com.elhady.movies.core.common.presentation.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.PeopleEntity
import com.elhady.movies.core.common.presentation.model.PeopleUIState
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
