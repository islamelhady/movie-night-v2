package com.elhady.movies.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.R
import com.elhady.movies.core.ui.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.state.MovieHorizontalUiState

class MovieHorizontalAdapter(
    private val listener: MovieAdapterListener
) : ListAdapter<MovieHorizontalUiState, MovieHorizontalAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieHorizontalBinding>(
            layoutInflater, R.layout.item_movie_horizontal, parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class MovieViewHolder(private val binding: ItemMovieHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieHorizontalUiState, listener: MovieAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieHorizontalUiState>() {
        override fun areItemsTheSame(
            oldItem: MovieHorizontalUiState,
            newItem: MovieHorizontalUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MovieHorizontalUiState,
            newItem: MovieHorizontalUiState
        ): Boolean = oldItem == newItem
    }
}
