package com.elhady.movies.domain.mappers.search

import com.elhady.movies.data.local.database.entity.SearchHistoryEntity
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.SearchHistory
import javax.inject.Inject

class SearchHistoryMapper @Inject constructor() : Mapper<SearchHistoryEntity, SearchHistory> {
    override fun map(input: SearchHistoryEntity): SearchHistory {
        return SearchHistory(
            name = input.keyword
        )
    }
}