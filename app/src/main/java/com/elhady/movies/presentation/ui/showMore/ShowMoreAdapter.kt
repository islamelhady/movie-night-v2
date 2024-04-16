package com.elhady.movies.presentation.ui.showMore

import androidx.recyclerview.widget.DiffUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BasePagingAdapter
import com.elhady.movies.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.presentation.viewmodel.showmore.ShowMoreListener
import com.elhady.movies.presentation.viewmodel.showmore.ShowMoreUi

class ShowMoreAdapter(
    listener: ShowMoreListener
) :
    BasePagingAdapter<ShowMoreUi, ItemMovieHorizontalBinding>(ShowComparator, listener) {
    override val layoutId: Int = R.layout.show_more_item_horizontal

    object ShowComparator : DiffUtil.ItemCallback<ShowMoreUi>() {
        override fun areItemsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi) =
            oldItem == newItem
    }
}