package com.elhady.movies.ui.tvShowDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class TvShowDetailsAdapter(private var detailsItem: List<SeriesItems>, val listener: BaseInteractionListener): BaseAdapter<SeriesItems>(detailsItem, listener) {
    override val layoutID: Int = 0

    override fun setItems(newItems: List<SeriesItems>) {
        detailsItem = newItems.sortedBy {
            it.priority
        }
        super.setItems(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder as ItemViewHolder, position)
    }

    private fun bind(holder: ItemViewHolder, position: Int){
        when(val currentItem = detailsItem[position]){
            is SeriesItems.Header -> {
                holder.binding.setVariable(BR.item, currentItem.data)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(detailsItem[position]){
            is SeriesItems.Header -> R.layout.item_tv_show_details
        }
    }


}