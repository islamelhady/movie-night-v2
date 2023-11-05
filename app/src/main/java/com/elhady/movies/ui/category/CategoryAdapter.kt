package com.elhady.movies.ui.category

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BasePagingAdapter
import com.elhady.movies.ui.models.MediaUiState

class CategoryAdapter(listener: MediaInteractionListener) :
    BasePagingAdapter<MediaUiState>(CategoryComparator, listener) {
    override val layoutID: Int = 0

    object CategoryComparator : DiffUtil.ItemCallback<MediaUiState>() {
        override fun areItemsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MediaUiState,
            newItem: MediaUiState
        ): Boolean {
            return true
        }
    }
}