package com.elhady.movies.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.BR
import com.elhady.movies.databinding.HorizontalRecyclerBinding
import com.elhady.movies.ui.home.HomeViewModel
import com.elhady.movies.ui.home.adapters.CategoryAdapter

abstract class HorizontalBaseAdapter<T>(
    private val adapter: BaseAdapter<T>,
    private val viewModel: BaseViewModel
) : RecyclerView.Adapter<HorizontalBaseAdapter.HorizontalWrapperViewHolder<T>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalWrapperViewHolder<T> {
        return HorizontalWrapperViewHolder(
            HorizontalRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HorizontalWrapperViewHolder<T>, position: Int) {
        holder.bind(adapter, viewModel)
    }

    override fun getItemCount(): Int = 1

    class HorizontalWrapperViewHolder<T>(val binding: HorizontalRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: BaseAdapter<T>, viewModel: BaseViewModel) {
            binding.recyclerView.adapter = adapter
            binding.viewModel = viewModel as HomeViewModel?
        }
    }
}