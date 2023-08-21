package com.elhady.movies.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.databinding.HorizontalRecyclerBinding

class HorizontalWrapperAdapter(val adapter: CategoryAdapter) :
    RecyclerView.Adapter<HorizontalWrapperAdapter.HorizontalWrapperViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalWrapperViewHolder {
        return HorizontalWrapperViewHolder(
            HorizontalRecyclerBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HorizontalWrapperViewHolder, position: Int) {
        holder.bind(adapter)
    }

    override fun getItemCount(): Int = 1
    class HorizontalWrapperViewHolder(private val binding: HorizontalRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: CategoryAdapter) {
            binding.recyclerView.adapter = adapter
        }
    }
}
