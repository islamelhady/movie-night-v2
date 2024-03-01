package com.elhady.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.elhady.base.BaseInteractionListener
import com.elhady.base.BasePagingAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.models.MediaUiState

class ActorSearchAdapter(listener: ActorSearchInteractionListener): BasePagingAdapter<MediaUiState>(
    ActorSearchComparator, listener) {
    override val layoutID: Int = R.layout.item_actor_search

    object ActorSearchComparator: DiffUtil.ItemCallback<MediaUiState>(){
        override fun areItemsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean {
            return true
        }
    }
}

interface ActorSearchInteractionListener: BaseInteractionListener {
    fun onClickActor(actorId: Int)
}