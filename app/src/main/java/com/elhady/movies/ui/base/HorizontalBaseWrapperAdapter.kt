package com.elhady.movies.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.databinding.HorizontalRecyclerBinding

class HorizontalBaseWrapperAdapter<T>(val adapter: T) :
    RecyclerView.Adapter<HorizontalBaseWrapperAdapter.HorizontalBaseWrapperViewHolder<T>>() {
    class HorizontalBaseWrapperViewHolder<T>(val binding: HorizontalRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: Any) {
            binding.recyclerView.adapter = adapter as RecyclerView.Adapter<*>
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalBaseWrapperViewHolder<T> {
        return HorizontalBaseWrapperViewHolder(
            HorizontalRecyclerBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: HorizontalBaseWrapperViewHolder<T>, position: Int) {
        holder.bind(adapter as RecyclerView.Adapter<*>)
    }
}