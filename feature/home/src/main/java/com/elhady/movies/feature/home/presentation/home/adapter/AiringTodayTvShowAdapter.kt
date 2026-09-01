package com.elhady.movies.feature.home.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeItemAiringTodayBinding
import com.elhady.movies.feature.home.presentation.home.AiringTodayTvShowUiState
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener

class AiringTodayTvShowAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<AiringTodayTvShowUiState, AiringTodayTvShowAdapter.AiringTodayViewHolder>(AiringTodayDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringTodayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomeItemAiringTodayBinding>(
            layoutInflater, R.layout.home_item_airing_today, parent, false
        )
        return AiringTodayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AiringTodayViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class AiringTodayViewHolder(private val binding: HomeItemAiringTodayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AiringTodayTvShowUiState, listener: HomeAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class AiringTodayDiffCallback : DiffUtil.ItemCallback<AiringTodayTvShowUiState>() {
        override fun areItemsTheSame(
            oldItem: AiringTodayTvShowUiState,
            newItem: AiringTodayTvShowUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AiringTodayTvShowUiState,
            newItem: AiringTodayTvShowUiState
        ): Boolean = oldItem == newItem
    }
}
