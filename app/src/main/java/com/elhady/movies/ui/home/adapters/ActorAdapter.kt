package com.elhady.movies.ui.home.adapters

import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.ActorUiState

class ActorAdapter(items: List<ActorUiState>, val listener: ActorInteractionListener, layout: Int): BaseAdapter<ActorUiState>(items, listener) {
    override val layoutID: Int = layout
}

interface ActorInteractionListener: BaseInteractionListener{
    fun onClickActor(actorID: Int)
}