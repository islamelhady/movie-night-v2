package com.elhady.movies.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.BR

abstract class BasePagingAdapter<T : Any>(
    diffUtilCallback: DiffUtil.ItemCallback<T>,
    val listener: BaseInteractionListener
) :
    PagingDataAdapter<T, BasePagingAdapter.BaseViewHolder>(diffUtilCallback) {

    abstract val layoutID: Int
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
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
        if (holder is ItemViewHolder)
            getItem(position)?.let {
                bind(it = it, holder)
            }
    }

    private fun <T> bind(it: T, holder: ItemViewHolder) {
        holder.binding.apply {
            setVariable(BR.item, it)
            setVariable(BR.listener, listener)
        }
    }

    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)
    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}