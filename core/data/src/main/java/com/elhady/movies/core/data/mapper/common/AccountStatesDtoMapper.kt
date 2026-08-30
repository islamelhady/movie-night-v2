package com.elhady.movies.core.data.mapper.common

import com.elhady.movies.core.domain.model.common.AccountStates
import com.elhady.movies.core.network.dto.common.AccountStatesDto
import javax.inject.Inject

class AccountStatesDtoMapper @Inject constructor() {
    fun map(input: AccountStatesDto?): AccountStates {
        return AccountStates(
            favorite = input?.favorite ?: false,
            watchlist = input?.watchlist ?: false,
            id = input?.id ?: 0,
            rating = (input?.rated as? Map<*, *>)?.get("value") as? Double ?: 0.0
        )
    }
}
