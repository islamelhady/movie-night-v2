package com.elhady.movies.presentation.ui.tvshows

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BasePagingAdapter
import com.elhady.movies.databinding.ItemTvShowBinding
import com.elhady.movies.presentation.viewmodel.tvshows.TVShowsListener
import com.elhady.movies.presentation.viewmodel.tvshows.TVShowsUI

class TVShowsAdapter(listener: TVShowsListener) :
    BasePagingAdapter<TVShowsUI, ItemTvShowBinding>(Comparator, listener) {


    override val layoutId = R.layout.item_tv_show

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

