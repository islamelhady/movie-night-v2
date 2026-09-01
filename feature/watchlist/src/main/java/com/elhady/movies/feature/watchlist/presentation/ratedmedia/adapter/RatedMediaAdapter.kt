package com.elhady.movies.feature.watchlist.presentation.ratedmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.feature.watchlist.BR

class RatedMediaAdapter(
    private val listener: MovieAdapterListener
) : PagingDataAdapter<MovieHorizontalUiState, RatedMediaAdapter.RatedMediaViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatedMediaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieHorizontalBinding>(
            layoutInflater, com.elhady.movies.core.ui.R.layout.item_movie_horizontal, parent, false
        )
        return RatedMediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatedMediaViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }

    class RatedMediaViewHolder(private val binding: ItemMovieHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieHorizontalUiState, listener: MovieAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    object Comparator : DiffUtil.ItemCallback<MovieHorizontalUiState>() {
        override fun areItemsTheSame(oldItem: MovieHorizontalUiState, newItem: MovieHorizontalUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieHorizontalUiState, newItem: MovieHorizontalUiState): Boolean {
            return oldItem == newItem
        }
    }
}
