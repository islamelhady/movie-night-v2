package com.elhady.movies.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.BR

abstract class HorizontalBaseAdapter<M, T>(
    private val viewModel: M
) : RecyclerView.Adapter<HorizontalBaseAdapter.BaseViewHolder>() {

    abstract val layoutID: Int
    abstract val adapter: T

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutID,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ItemViewHolder) bind(holder)
    }

    override fun getItemCount(): Int = 1

    private fun bind(holder: ItemViewHolder) {
        holder.binding.apply {
            setVariable(BR.adapter, adapter)
            setVariable(BR.viewModel, viewModel)
        }
    }

    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)
    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}