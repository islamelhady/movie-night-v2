package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.PeopleListener
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState

class PeopleAdapter(
    list: List<PeopleUIState>,
    listener: PeopleListener
) : BaseAdapter<PeopleUIState>(list, listener) {
    override val layoutID = R.layout.item_people
}