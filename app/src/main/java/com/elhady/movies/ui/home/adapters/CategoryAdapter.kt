package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.data.remote.test.Category
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class CategoryAdapter(items: List<Category>, listener: CategoryInteractionListener) :
    BaseAdapter<Category>(items, listener) {
    override val layoutID: Int = R.layout.item_category
    override fun areItemContent(oldItem: Category, newItem: Category): Boolean {
        return true
    }
}

interface CategoryInteractionListener : BaseInteractionListener {
    fun onClickCategory(name: String)
}