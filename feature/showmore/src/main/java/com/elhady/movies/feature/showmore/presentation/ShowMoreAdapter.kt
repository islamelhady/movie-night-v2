package com.elhady.movies.feature.showmore.presentation

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.feature.showmore.BR
import com.elhady.movies.feature.showmore.R
import com.elhady.movies.core.common.bases.BasePagingAdapter
import com.elhady.movies.core.ui.databinding.ItemMovieHorizontalBinding

class ShowMoreAdapter(
    listener: ShowMoreListener
) :
    BasePagingAdapter<ShowMoreUi, ItemMovieHorizontalBinding>(ShowComparator, listener) {
    override val layoutId: Int = R.layout.show_more_item_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    object ShowComparator : DiffUtil.ItemCallback<ShowMoreUi>() {
        override fun areItemsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi) =
            oldItem == newItem
    }
}
