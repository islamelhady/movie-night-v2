package com.elhady.movies.ui.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class DetailsAdapter(
    private val detailsItems: List<DetailsItem>,
    val listener: BaseInteractionListener
) : BaseAdapter<DetailsItem>(detailsItems, listener) {
    override val layoutID: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder as ItemViewHolder, position)
    }

    private fun bind(holder: ItemViewHolder, position: Int){
        when (val currentItem = detailsItems[position]){
            is DetailsItem.Header -> {
                holder.run {
                    binding.setVariable(BR.item, currentItem.data)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(detailsItems[position]){
            is DetailsItem.Header -> R.layout.item_movie_details
        }

    }


}