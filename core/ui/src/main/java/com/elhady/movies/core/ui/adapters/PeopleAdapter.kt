package com.elhady.movies.core.ui.adapters

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.core.ui.listener.PeopleListener
import com.elhady.movies.core.ui.model.PeopleUIState

class PeopleAdapter(
    list: List<PeopleUIState>,
    listener: PeopleListener
) : BaseAdapter<PeopleUIState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_people
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
