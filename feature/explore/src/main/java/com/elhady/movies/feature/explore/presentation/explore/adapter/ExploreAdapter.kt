package com.elhady.movies.feature.explore.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.feature.explore.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.explore.R
import com.elhady.movies.feature.explore.databinding.ExploreItemTrendingMovieGridBinding
import com.elhady.movies.feature.explore.databinding.ExploreItemTrendingMovieHorizontalBinding
import com.elhady.movies.feature.explore.presentation.explore.ExploreItem
import com.elhady.movies.feature.explore.presentation.explore.ExploreAdapterListener
import com.elhady.movies.feature.explore.presentation.explore.LayoutItemType

class ExploreAdapter(
    private var list: MutableList<ExploreItem>,
    private val listener: ExploreAdapterListener
) : BaseAdapter<ExploreItem>(list, listener) {
    override val layoutID: Int = 0
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
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

            else -> throw Exception("UNKNOWN VIEW HOLDER")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is GridViewHolder -> bindGrid(holder, position)
            is HorizontalViewHolder -> bindHorizontal(holder, position)
        }
    }

    private fun bindGrid(holder: GridViewHolder, position: Int) {
        val grid = list[position] as ExploreItem.GridItem
        holder.binding.item = grid.gridItem
        holder.binding.listener = listener

    }

    private fun bindHorizontal(holder: HorizontalViewHolder, position: Int) {
        val horizontal = list[position] as ExploreItem.HorizontalItem
        holder.binding.item = horizontal.horizontalItem
        holder.binding.listener = listener
    }


    override fun setItems(newItems: List<ExploreItem>) {
        list = newItems.sortedBy { it.type.ordinal }.toMutableList()
        super.setItems(newItems)
    }


    override fun getItemViewType(position: Int): Int = list[position].type.ordinal

    class GridViewHolder(val binding: ExploreItemTrendingMovieGridBinding) : BaseViewHolder(binding)

    class HorizontalViewHolder(val binding: ExploreItemTrendingMovieHorizontalBinding) :
        BaseViewHolder(binding)

}
