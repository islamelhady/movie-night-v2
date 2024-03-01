package com.elhady.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

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