package com.elhady.movies.feature.showmore.presentation.showmore.adapter

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.feature.showmore.BR
import com.elhady.movies.feature.showmore.R
import com.elhady.movies.core.ui.base.BasePagingAdapter
import com.elhady.movies.feature.showmore.databinding.ShowMoreItemHorizontalBinding
import com.elhady.movies.feature.showmore.presentation.showmore.ShowMoreUi
import com.elhady.movies.feature.showmore.presentation.showmore.ShowMoreAdapterListener

class ShowMoreAdapter(
    listener: ShowMoreAdapterListener
) :
    BasePagingAdapter<ShowMoreUi, ShowMoreItemHorizontalBinding>(ShowComparator, listener) {
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
