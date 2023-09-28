package com.elhady.movies.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.home.HomeItem

class HomeAdapter(
    private var homeItems: List<HomeItem>,
    private val listener: BaseInteractionListener
) :
    BaseAdapter<HomeItem>(homeItems, listener) {
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

    private fun bind(holder: ItemViewHolder, position: Int) {
        when (val currentHomeItem = homeItems[position]) {
            is HomeItem.Slider -> {
                holder.binding.setVariable(BR.adapterRecycler, PopularMovieAdapter(currentHomeItem.items, listener as MovieInteractionListener))
            }
        }
    }

    override fun setItems(newItems: List<HomeItem>) {
        homeItems = newItems.toMutableList()
        super.setItems(newItems)
    }


    override fun areItemContent(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int {
            return when (homeItems[position]) {
                is HomeItem.Slider -> R.layout.list_popular_movie
            }
    }


}