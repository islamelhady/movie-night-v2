package com.elhady.movies.feature.showmore.presentation.showmore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.showmore.BR
import com.elhady.movies.feature.showmore.R
import com.elhady.movies.feature.showmore.databinding.ShowMoreItemHorizontalBinding
import com.elhady.movies.feature.showmore.presentation.showmore.ShowMoreUi
import com.elhady.movies.feature.showmore.presentation.showmore.ShowMoreAdapterListener

class ShowMoreAdapter(
    private val listener: ShowMoreAdapterListener
) : PagingDataAdapter<ShowMoreUi, ShowMoreAdapter.ShowMoreViewHolder>(ShowComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowMoreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ShowMoreItemHorizontalBinding>(
            layoutInflater, R.layout.show_more_item_horizontal, parent, false
        )
        return ShowMoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowMoreViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }

    class ShowMoreViewHolder(private val binding: ShowMoreItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowMoreUi, listener: ShowMoreAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    object ShowComparator : DiffUtil.ItemCallback<ShowMoreUi>() {
        override fun areItemsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShowMoreUi, newItem: ShowMoreUi) =
            oldItem == newItem
    }
}
