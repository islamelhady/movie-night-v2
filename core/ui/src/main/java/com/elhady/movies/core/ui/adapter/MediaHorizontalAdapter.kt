package com.elhady.movies.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.R
import com.elhady.movies.core.ui.databinding.ItemMediaHorizontalBinding
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.state.MediaHorizontalUiState

class MediaHorizontalAdapter(
    private val listener: MediaListener
) : ListAdapter<MediaHorizontalUiState, MediaHorizontalAdapter.MediaViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMediaHorizontalBinding>(
            layoutInflater, R.layout.item_media_horizontal, parent, false
        )
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class MediaViewHolder(private val binding: ItemMediaHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaHorizontalUiState, listener: MediaListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class MediaDiffCallback : DiffUtil.ItemCallback<MediaHorizontalUiState>() {
        override fun areItemsTheSame(
            oldItem: MediaHorizontalUiState,
            newItem: MediaHorizontalUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MediaHorizontalUiState,
            newItem: MediaHorizontalUiState
        ): Boolean = oldItem == newItem
    }
}
