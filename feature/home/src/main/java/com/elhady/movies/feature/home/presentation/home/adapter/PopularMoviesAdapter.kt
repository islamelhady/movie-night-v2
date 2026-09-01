package com.elhady.movies.feature.home.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeItemPopularMoviesBinding
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.PopularMovieUiState

class PopularMoviesAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<PopularMovieUiState, PopularMoviesAdapter.PopularMoviesViewHolder>(PopularMoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomeItemPopularMoviesBinding>(
            layoutInflater, R.layout.home_item_popular_movies, parent, false
        )
        return PopularMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PopularMoviesViewHolder(private val binding: HomeItemPopularMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularMovieUiState, listener: HomeAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class PopularMoviesDiffCallback : DiffUtil.ItemCallback<PopularMovieUiState>() {
        override fun areItemsTheSame(
            oldItem: PopularMovieUiState,
            newItem: PopularMovieUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PopularMovieUiState,
            newItem: PopularMovieUiState
        ): Boolean = oldItem == newItem
    }
}
