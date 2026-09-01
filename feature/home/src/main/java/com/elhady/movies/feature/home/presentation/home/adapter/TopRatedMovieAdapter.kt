package com.elhady.movies.feature.home.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeItemTopRatedBinding
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.TopRatedMovieUiState

class TopRatedMovieAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<TopRatedMovieUiState, TopRatedMovieAdapter.TopRatedViewHolder>(TopRatedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomeItemTopRatedBinding>(
            layoutInflater, R.layout.home_item_top_rated, parent, false
        )
        return TopRatedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class TopRatedViewHolder(private val binding: HomeItemTopRatedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopRatedMovieUiState, listener: HomeAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class TopRatedDiffCallback : DiffUtil.ItemCallback<TopRatedMovieUiState>() {
        override fun areItemsTheSame(
            oldItem: TopRatedMovieUiState,
            newItem: TopRatedMovieUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: TopRatedMovieUiState,
            newItem: TopRatedMovieUiState
        ): Boolean = oldItem == newItem
    }
}
