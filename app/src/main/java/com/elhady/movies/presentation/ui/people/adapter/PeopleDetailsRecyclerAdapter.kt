package com.elhady.movies.presentation.ui.people.adapter

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.people.PeopleDetailsListener
import com.elhady.movies.presentation.viewmodel.people.PersonDetailsUiState


class  PeopleDetailsRecyclerAdapter(items: List<PersonDetailsUiState.PeopleMediaUiState>, listener: PeopleDetailsListener):
    BaseAdapter<PersonDetailsUiState.PeopleMediaUiState>(items, listener) {

    override val layoutID = R.layout.item_people_media
}