package com.elhady.movies.ui.allMedia

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.base.BasePagingAdapter
import com.elhady.movies.ui.models.MediaUiState

class AllMediaAdapter(listener: BaseInteractionListener) :
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