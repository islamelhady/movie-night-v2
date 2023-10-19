package com.elhady.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.databinding.ItemLoadStateBinding

class LoadAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadAdapter.LoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        return LoadViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        bind(holder, loadState)
    }

    private fun bind(holder: LoadViewHolder, loadState: LoadState) {
        holder.binding.progress.isVisible = loadState is LoadState.Loading
        holder.binding.buttonRetry.isVisible = loadState is LoadState.Error
        holder.binding.textInternetConnection.isVisible = loadState is LoadState.Error
    }


    inner class LoadViewHolder(val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}