package com.elhady.movies.feature.watchlist.presentation.listcontents.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.ItemListsDetailsBinding
import com.elhady.movies.feature.watchlist.presentation.listcontents.MovieUiState
import com.elhady.movies.feature.watchlist.presentation.listcontents.ListContentsAdapterListener

class ListContentsAdapter(
    private val listener: ListContentsAdapterListener
) : ListAdapter<MovieUiState, ListContentsAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemListsDetailsBinding>(
            layoutInflater, R.layout.item_lists_details, parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class MovieViewHolder(private val binding: ItemListsDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieUiState, listener: ListContentsAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieUiState>() {
        override fun areItemsTheSame(
            oldItem: MovieUiState,
            newItem: MovieUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MovieUiState,
            newItem: MovieUiState
        ): Boolean = oldItem == newItem
    }
}
