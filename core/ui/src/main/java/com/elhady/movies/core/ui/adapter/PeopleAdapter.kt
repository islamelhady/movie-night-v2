package com.elhady.movies.core.ui.adapter

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.PeopleListener
import com.elhady.movies.core.ui.state.PeopleUIState

class PeopleAdapter(
    list: List<PeopleUIState>,
    listener: PeopleListener
) : BaseAdapter<PeopleUIState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_people
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
