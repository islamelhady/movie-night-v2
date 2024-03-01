package com.elhady.ui.category

import androidx.recyclerview.widget.DiffUtil
import com.elhady.base.BasePagingAdapter
import com.elhady.ui.R
import com.elhady.ui.adapter.MediaInteractionListener
import com.elhady.viewmodel.models.MediaUiState

class CategoryAdapter(listener: MediaInteractionListener) :
    BasePagingAdapter<MediaUiState>(CategoryComparator, listener) {
    override val layoutID: Int = R.layout.item_category

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