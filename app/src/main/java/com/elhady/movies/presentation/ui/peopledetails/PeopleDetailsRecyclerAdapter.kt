package com.elhady.movies.presentation.ui.peopledetails

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.peopledetails.PeopleDetailsListener
import com.elhady.movies.presentation.viewmodel.peopledetails.PersonDetailsUiState


class  PeopleDetailsRecyclerAdapter(items: List<PersonDetailsUiState.PeopleMediaUiState>, listener: PeopleDetailsListener):
    BaseAdapter<PersonDetailsUiState.PeopleMediaUiState>(items, listener) {

    override val layoutID = R.layout.item_people_media
}