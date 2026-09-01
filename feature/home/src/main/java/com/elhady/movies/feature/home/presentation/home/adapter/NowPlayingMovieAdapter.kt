package com.elhady.movies.feature.home.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeItemNowPlayingBinding
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.NowPlayingMovieUiState

class NowPlayingMovieAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<NowPlayingMovieUiState, NowPlayingMovieAdapter.NowPlayingViewHolder>(NowPlayingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomeItemNowPlayingBinding>(
            layoutInflater, R.layout.home_item_now_playing, parent, false
        )
        return NowPlayingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class NowPlayingViewHolder(private val binding: HomeItemNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NowPlayingMovieUiState, listener: HomeAdapterListener) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    class NowPlayingDiffCallback : DiffUtil.ItemCallback<NowPlayingMovieUiState>() {
        override fun areItemsTheSame(
            oldItem: NowPlayingMovieUiState,
            newItem: NowPlayingMovieUiState
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NowPlayingMovieUiState,
            newItem: NowPlayingMovieUiState
        ): Boolean = oldItem == newItem
    }
}
