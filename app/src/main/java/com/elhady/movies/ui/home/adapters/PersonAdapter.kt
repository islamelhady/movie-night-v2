package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.domain.models.Person
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class PersonAdapter(items: List<Person>, listener: PersonInteractionListener) :
    BaseAdapter<Person>(items, listener) {
    override val layoutID: Int = R.layout.item_person
    override fun areItemContent(oldItem: Person, newItem: Person): Boolean {
        return true
    }
}
interface PersonInteractionListener : BaseInteractionListener {
    fun onClickCategory(name: String)
}