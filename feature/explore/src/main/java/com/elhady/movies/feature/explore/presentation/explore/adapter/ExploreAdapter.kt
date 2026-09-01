package com.elhady.movies.feature.explore.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.explore.BR
import com.elhady.movies.feature.explore.R
import com.elhady.movies.feature.explore.databinding.ExploreItemTrendingMovieGridBinding
import com.elhady.movies.feature.explore.databinding.ExploreItemTrendingMovieHorizontalBinding
import com.elhady.movies.feature.explore.presentation.explore.ExploreItem
import com.elhady.movies.feature.explore.presentation.explore.ExploreAdapterListener
import com.elhady.movies.feature.explore.presentation.explore.LayoutItemType

class ExploreAdapter(
    private val listener: ExploreAdapterListener
) : ListAdapter<ExploreItem, RecyclerView.ViewHolder>(ExploreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LayoutItemType.HORIZONTAL.ordinal -> {
                HorizontalViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.explore_item_trending_movie_horizontal, parent, false
                    )
                )
            }

            LayoutItemType.GRID.ordinal -> {
                GridViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.explore_item_trending_movie_grid, parent, false
                    )
                )
            }

            else -> throw IllegalArgumentException("UNKNOWN VIEW HOLDER")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is GridViewHolder -> holder.bind(item as ExploreItem.GridItem, listener)
            is HorizontalViewHolder -> holder.bind(item as ExploreItem.HorizontalItem, listener)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    class GridViewHolder(val binding: ExploreItemTrendingMovieGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExploreItem.GridItem, listener: ExploreAdapterListener) {
            binding.item = item.gridItem
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class HorizontalViewHolder(val binding: ExploreItemTrendingMovieHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExploreItem.HorizontalItem, listener: ExploreAdapterListener) {
            binding.item = item.horizontalItem
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class ExploreDiffCallback : DiffUtil.ItemCallback<ExploreItem>() {
        override fun areItemsTheSame(oldItem: ExploreItem, newItem: ExploreItem): Boolean {
            return when {
                oldItem is ExploreItem.HorizontalItem && newItem is ExploreItem.HorizontalItem -> true
                oldItem is ExploreItem.GridItem && newItem is ExploreItem.GridItem -> 
                    oldItem.gridItem.id == newItem.gridItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ExploreItem, newItem: ExploreItem): Boolean {
            return oldItem == newItem
        }
    }
}
