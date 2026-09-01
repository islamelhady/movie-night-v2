package com.elhady.movies.feature.home.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeItemImageSliderBinding
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.UpcomingMovieUiState

class UpcomingMovieAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<UpcomingMovieUiState, UpcomingMovieAdapter.UpcomingMovieViewHolder>(UpcomingMovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomeItemImageSliderBinding>(
            layoutInflater, R.layout.home_item_image_slider, parent, false
        )
        return UpcomingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class UpcomingMovieViewHolder(private val binding: HomeItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UpcomingMovieUiState, listener: HomeAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class UpcomingMovieDiffCallback : DiffUtil.ItemCallback<UpcomingMovieUiState>() {
        override fun areItemsTheSame(
            oldItem: UpcomingMovieUiState,
            newItem: UpcomingMovieUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: UpcomingMovieUiState,
            newItem: UpcomingMovieUiState
        ): Boolean = oldItem == newItem
    }
}
