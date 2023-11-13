package com.elhady.movies.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.base.BasePagingAdapter
import com.elhady.movies.ui.models.MediaUiState

class ActorSearchAdapter(listener: ActorSearchInteractionListener): BasePagingAdapter<MediaUiState>(ActorSearchComparator, listener) {
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

interface ActorSearchInteractionListener: BaseInteractionListener{
    fun onClickActor(actorId: Int, name: String)
}