package com.elhady.movies.ui.seriesDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.elhady.movies.R
import com.elhady.movies.ui.adapter.MediaAdapter
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.home.adapters.ActorAdapter
import com.elhady.movies.ui.home.adapters.ActorInteractionListener

class SeriesDetailsAdapter(private var detailsItem: List<SeriesItems>, val listener: BaseInteractionListener): BaseAdapter<SeriesItems>(detailsItem, listener) {
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

            is SeriesItems.Cast -> {
                holder.binding.setVariable(BR.adapterRecycler, ActorAdapter(items = currentItem.data, listener = listener as ActorInteractionListener, layout = R.layout.item_cast))
            }

            is SeriesItems.Similar -> {
                holder.binding.setVariable(BR.adapterRecycler, MediaAdapter(items = currentItem.data, listener = listener as MediaInteractionListener, layout = R.layout.item_similar ))
            }

            is SeriesItems.Season -> {
                holder.binding.setVariable(BR.adapterRecycler, SeasonAdapter(items = currentItem.data, listener = listener as SeasonInteractionListener))
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(detailsItem[position]){
            is SeriesItems.Header -> R.layout.item_series_details
            is SeriesItems.Cast -> R.layout.list_cast
            is SeriesItems.Similar -> R.layout.list_similar_series
            is SeriesItems.Season -> R.layout.list_season
        }
    }


}