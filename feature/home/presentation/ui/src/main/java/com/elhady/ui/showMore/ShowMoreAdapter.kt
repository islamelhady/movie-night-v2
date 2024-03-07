package com.elhady.ui.showMore

import androidx.recyclerview.widget.DiffUtil
import com.elhady.base.BasePagingAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.showMore.ShowMoreListener
import com.elhady.viewmodel.showMore.ShowMoreUi

class ShowMoreAdapter(listener: ShowMoreListener) :
    BasePagingAdapter<ShowMoreUi>(AllMediaComparator, listener) {

    override val layoutID: Int = R.layout.item_show_more_media

    object AllMediaComparator : DiffUtil.ItemCallback<ShowMoreUi>() {
        override fun areItemsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi): Boolean {
            return true
        }
    }

}