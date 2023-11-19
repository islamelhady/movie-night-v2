package com.elhady.movies.domain.mappers.favList

import com.elhady.movies.data.remote.response.CreatedListDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.CreatedList
import javax.inject.Inject

class CreatedListMapper @Inject constructor() : Mapper<CreatedListDto, CreatedList> {
    override fun map(input: CreatedListDto): CreatedList {
        return CreatedList(
            id = input.id ?: 0,
            name = input.name ?: "unknown",
            itemCount = input.itemCount ?: 0)
    }
}