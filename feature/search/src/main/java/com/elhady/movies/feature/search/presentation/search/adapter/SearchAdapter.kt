package com.elhady.movies.feature.search.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.search.R
import com.elhady.movies.core.ui.databinding.ItemMovieHorizontalBinding
import com.elhady.movies.feature.search.databinding.SearchItemPeopleBinding
import com.elhady.movies.feature.search.presentation.search.SearchItem
import com.elhady.movies.feature.search.presentation.search.SearchAdapterListener

class SearchAdapter(
    private val listener: SearchAdapterListener
) : ListAdapter<SearchItem, RecyclerView.ViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MEDIA -> {
                MediaViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        com.elhady.movies.core.ui.R.layout.item_movie_horizontal, parent, false
                    )
                )
            }

            VIEW_TYPE_PEOPLE -> {
                PeopleViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.search_item_people, parent, false
                    )
                )
            }

            else -> throw Exception("UNKNOWN VIEW HOLDER")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MediaViewHolder -> holder.bind(item as SearchItem.MediaItem, listener)
            is PeopleViewHolder -> holder.bind(item as SearchItem.PeopleItem, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchItem.MediaItem -> VIEW_TYPE_MEDIA
            is SearchItem.PeopleItem -> VIEW_TYPE_PEOPLE
        }
    }

    class MediaViewHolder(val binding: ItemMovieHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItem.MediaItem, listener: SearchAdapterListener) {
            binding.item = item.movieHorizontalUiState
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class PeopleViewHolder(val binding: SearchItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItem.PeopleItem, listener: SearchAdapterListener) {
            binding.item = item.peopleItem
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class SearchDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return when {
                oldItem is SearchItem.MediaItem && newItem is SearchItem.MediaItem -> 
                    oldItem.movieHorizontalUiState.id == newItem.movieHorizontalUiState.id
                oldItem is SearchItem.PeopleItem && newItem is SearchItem.PeopleItem -> 
                    oldItem.peopleItem.id == newItem.peopleItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val VIEW_TYPE_MEDIA = 0
        private const val VIEW_TYPE_PEOPLE = 1
    }
}
