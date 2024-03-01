package com.elhady.ui.home.adapters

import com.elhady.base.BaseInteractionListener
import com.elhady.viewmodel.models.ActorUiState


class ActorAdapter(items: List<ActorUiState>, val listener: ActorInteractionListener, layout: Int): BaseAdapter<ActorUiState>(items, listener) {
    override val layoutID: Int = layout
}

interface ActorInteractionListener: BaseInteractionListener {
    fun onClickActor(actorID: Int)
}