package com.elhady.movies.feature.watchlist.presentation.watchhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.WatchHistoryRecyclerViewCardBinding
import com.elhady.movies.feature.watchlist.databinding.WatchHistoryRecyclerViewTitleBinding
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.feature.watchlist.presentation.watchhistory.WatchHistoryRecyclerItem

class WatchHistoryAdapter(
    private val listener: MediaListener,
) : ListAdapter<WatchHistoryRecyclerItem, RecyclerView.ViewHolder>(WatchHistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TITLE_ITEM -> TitleViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.watch_history_recycler_view_title, parent, false)
            )
            CARD_ITEM -> CardViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.watch_history_recycler_view_card, parent, false)
            )
            else -> throw Exception("item not found")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is TitleViewHolder -> holder.bind(item as WatchHistoryRecyclerItem.Title)
            is CardViewHolder -> holder.bind(item as WatchHistoryRecyclerItem.MovieCard, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is WatchHistoryRecyclerItem.Title -> TITLE_ITEM
            is WatchHistoryRecyclerItem.MovieCard -> CARD_ITEM
        }
    }

    class TitleViewHolder(val binding: WatchHistoryRecyclerViewTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WatchHistoryRecyclerItem.Title) {
            binding.item = item.title
            binding.executePendingBindings()
        }
    }

    class CardViewHolder(val binding: WatchHistoryRecyclerViewCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WatchHistoryRecyclerItem.MovieCard, listener: MediaListener) {
            binding.item = item.movie
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class WatchHistoryDiffCallback : DiffUtil.ItemCallback<WatchHistoryRecyclerItem>() {
        override fun areItemsTheSame(oldItem: WatchHistoryRecyclerItem, newItem: WatchHistoryRecyclerItem): Boolean {
            return when {
                oldItem is WatchHistoryRecyclerItem.MovieCard && newItem is WatchHistoryRecyclerItem.MovieCard -> 
                    oldItem.movie.id == newItem.movie.id
                oldItem is WatchHistoryRecyclerItem.Title && newItem is WatchHistoryRecyclerItem.Title -> 
                    oldItem.title == newItem.title
                else -> false
            }
        }
        override fun areContentsTheSame(oldItem: WatchHistoryRecyclerItem, newItem: WatchHistoryRecyclerItem): Boolean {
            return oldItem == newItem
        }
    }

    private companion object {
        const val TITLE_ITEM = 21
        const val CARD_ITEM = 14
    }
}
