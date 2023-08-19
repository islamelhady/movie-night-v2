package com.elhady.movies.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private val items: List<T>) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    abstract val layoutID: Int

    fun setItem(newItems: List<T>) {
        val diffUtil = DiffUtil.calculateDiff(
            BaseDiffUtil(
                items,
                newItems,
                { oldItem, newItem -> areItemSame(oldItem, newItem) },
                { oldItem, newItem -> areItemContent(oldItem, newItem) })
        )
    }

    open fun areItemSame(oldItem: T, newItem: T): Boolean {
        return oldItem?.equals(newItem) == true
    }

    abstract fun areItemContent(oldItem: T, newItem: T): Boolean

    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}