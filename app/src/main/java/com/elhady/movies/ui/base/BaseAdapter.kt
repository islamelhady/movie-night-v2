package com.elhady.movies.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.BR

interface BaseInteractionListener
abstract class BaseAdapter<T>(
    private var items: List<T>,
    private val listener: BaseInteractionListener
): RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    abstract val layoutID: Int

    fun setItem(newItems: List<T>) {
        val diffUtil = DiffUtil.calculateDiff(
            BaseDiffUtil(
                items,
                newItems,
                { oldItem, newItem -> areItemSame(oldItem, newItem) },
                { oldItem, newItem -> areItemContent(oldItem, newItem) })
        )
        items = newItems
        diffUtil.dispatchUpdatesTo(this)
    }

    open fun areItemSame(oldItem: T, newItem: T): Boolean {
        return oldItem?.equals(newItem) == true
    }

    abstract fun areItemContent(oldItem: T, newItem: T): Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutID,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ItemViewHolder) bind(holder, position)
    }

    override fun getItemCount() = items.size

    private fun bind(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, items[position])
        }
    }


    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)

    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}