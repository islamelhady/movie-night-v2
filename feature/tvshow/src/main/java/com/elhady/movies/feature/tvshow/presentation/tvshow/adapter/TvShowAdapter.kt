package com.elhady.movies.feature.tvshow.presentation.tvshow.adapter

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.feature.tvshow.BR
import com.elhady.movies.feature.tvshow.R
import com.elhady.movies.core.ui.base.BasePagingAdapter
import com.elhady.movies.feature.tvshow.databinding.ItemTvShowBinding
import com.elhady.movies.feature.tvshow.presentation.tvshow.ShowUiState
import com.elhady.movies.feature.tvshow.presentation.tvshow.TvShowAdapterListener

class TvShowAdapter(listener: TvShowAdapterListener) :
    BasePagingAdapter<ShowUiState, ItemTvShowBinding>(Comparator, listener) {


    override val layoutId = R.layout.item_tv_show
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    object Comparator : DiffUtil.ItemCallback<ShowUiState>() {
        override fun areItemsTheSame(oldItem: ShowUiState, newItem: ShowUiState): Boolean {
            return oldItem.tvId == newItem.tvId
        }

        override fun areContentsTheSame(
            oldItem: ShowUiState,
            newItem: ShowUiState
        ): Boolean {
            return oldItem == newItem
        }
    }
}
