package com.elhady.movies.feature.details.presentation.tvdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.R
import com.elhady.movies.core.ui.databinding.ItemMediaVerticalBinding
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.state.MediaVerticalUiState

class RecommendedAdapter(
    private val listener: MediaListener
) : ListAdapter<MediaVerticalUiState, RecommendedAdapter.RecommendedViewHolder>(RecommendedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMediaVerticalBinding>(
            layoutInflater, R.layout.item_media_vertical, parent, false
        )
        return RecommendedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class RecommendedViewHolder(private val binding: ItemMediaVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaVerticalUiState, listener: MediaListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class RecommendedDiffCallback : DiffUtil.ItemCallback<MediaVerticalUiState>() {
        override fun areItemsTheSame(
            oldItem: MediaVerticalUiState,
            newItem: MediaVerticalUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MediaVerticalUiState,
            newItem: MediaVerticalUiState
        ): Boolean = oldItem == newItem
    }
}
