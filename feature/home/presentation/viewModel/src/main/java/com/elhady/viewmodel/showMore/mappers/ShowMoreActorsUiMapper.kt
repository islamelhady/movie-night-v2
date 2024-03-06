package com.elhady.viewmodel.showMore.mappers

import com.elhady.entities.ActorEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.showMore.ShowMoreUi
import javax.inject.Inject

class ShowMoreActorsUiMapper @Inject constructor() :
    Mapper<ActorEntity, ShowMoreUi> {
    override fun map(input: ActorEntity): ShowMoreUi {
        return ShowMoreUi(
            id = input.id,
            name = input.characterName,
            imageUrl = input.imageUrl,
        )
    }

}