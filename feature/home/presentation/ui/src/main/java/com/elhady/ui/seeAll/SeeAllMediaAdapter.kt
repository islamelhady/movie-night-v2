package com.elhady.ui.seeAll

import androidx.recyclerview.widget.DiffUtil
import com.elhady.base.BasePagingAdapter
import com.elhady.ui.adapter.MediaInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.models.MediaUiState

class SeeAllMediaAdapter(listener: MediaInteractionListener) :
    BasePagingAdapter<MediaUiState>(AllMediaComparator, listener) {

    override val layoutID: Int = R.layout.item_all_media

    object AllMediaComparator : DiffUtil.ItemCallback<MediaUiState>() {
        override fun areItemsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean {
            return true
        }
    }

}