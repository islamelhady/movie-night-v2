package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter

class HorizontalAdapter(private val items: List<String>): BaseAdapter<String>(items) {
    override val layoutID: Int = R.layout.view_horizontal


    override fun areItemContent(oldItem: String, newItem: String): Boolean {
        return true
    }
}