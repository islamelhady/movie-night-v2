package com.elhady.movies.presentation.viewmodel.tv_shows

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.TVShowsEntity

import javax.inject.Inject

class TVShowsMapper @Inject constructor() :
    Mapper<TVShowsEntity, TVShowsUI> {
    override fun map(input: TVShowsEntity): TVShowsUI {
        return TVShowsUI(
            tvId = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}