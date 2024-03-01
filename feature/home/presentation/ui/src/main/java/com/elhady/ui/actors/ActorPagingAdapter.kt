package com.elhady.ui.actors

import androidx.recyclerview.widget.DiffUtil
import com.elhady.base.BaseInteractionListener
import com.elhady.base.BasePagingAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.models.ActorUiState

class ActorPagingAdapter(listener: BaseInteractionListener) :
    BasePagingAdapter<ActorUiState>(ActorComparator, listener) {

    override val layoutID: Int = R.layout.item_all_actor

    object ActorComparator : DiffUtil.ItemCallback<ActorUiState>() {
        override fun areItemsTheSame(oldItem: ActorUiState, newItem: ActorUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ActorUiState, newItem: ActorUiState): Boolean {
            return true
        }
    }

}