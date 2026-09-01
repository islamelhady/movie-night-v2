package com.elhady.movies.feature.watchlist.presentation.lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.ItemListBinding
import com.elhady.movies.feature.watchlist.presentation.lists.ListMovieUiState
import com.elhady.movies.feature.watchlist.presentation.lists.ListsAdapterListener

class ListsAdapter(
    private val listener: ListsAdapterListener
) : ListAdapter<ListMovieUiState, ListsAdapter.ListViewHolder>(ListsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemListBinding>(
            layoutInflater, R.layout.item_list, parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListMovieUiState, listener: ListsAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class ListsDiffCallback : DiffUtil.ItemCallback<ListMovieUiState>() {
        override fun areItemsTheSame(
            oldItem: ListMovieUiState,
            newItem: ListMovieUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ListMovieUiState,
            newItem: ListMovieUiState
        ): Boolean = oldItem == newItem
    }
}
