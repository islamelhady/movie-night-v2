package com.elhady.movies.feature.home.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeItemTrendingBinding
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.TrendingMovieUiState

class TrendingMovieAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<TrendingMovieUiState, TrendingMovieAdapter.TrendingViewHolder>(TrendingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomeItemTrendingBinding>(
            layoutInflater, R.layout.home_item_trending, parent, false
        )
        return TrendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class TrendingViewHolder(private val binding: HomeItemTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrendingMovieUiState, listener: HomeAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class TrendingDiffCallback : DiffUtil.ItemCallback<TrendingMovieUiState>() {
        override fun areItemsTheSame(
            oldItem: TrendingMovieUiState,
            newItem: TrendingMovieUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: TrendingMovieUiState,
            newItem: TrendingMovieUiState
        ): Boolean = oldItem == newItem
    }
}
