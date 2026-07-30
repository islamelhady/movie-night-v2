package com.elhady.movies.feature.tvshow.presentation

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.feature.tvshow.BR
import com.elhady.movies.feature.tvshow.R
import com.elhady.movies.core.ui.base.BasePagingAdapter
import com.elhady.movies.feature.tvshow.databinding.ItemTvShowBinding

class TVShowsAdapter(listener: TVShowsListener) :
    BasePagingAdapter<TVShowsUI, ItemTvShowBinding>(Comparator, listener) {


    override val layoutId = R.layout.item_tv_show
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    object Comparator : DiffUtil.ItemCallback<TVShowsUI>() {
        override fun areItemsTheSame(oldItem: TVShowsUI, newItem: TVShowsUI): Boolean {
            return oldItem.tvId == newItem.tvId
        }

        override fun areContentsTheSame(
            oldItem: TVShowsUI,
            newItem: TVShowsUI
        ): Boolean {
            return oldItem == newItem
        }
    }
}
