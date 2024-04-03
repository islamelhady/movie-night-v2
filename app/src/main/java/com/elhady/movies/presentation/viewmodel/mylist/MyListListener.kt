package com.elhady.movies.presentation.viewmodel.mylist

import com.elhady.movies.core.bases.BaseInteractionListener

interface MyListListener : BaseInteractionListener {

    fun onClickItem(listId: Int , listType: String = "movie", listName: String = "favorite")

    fun onClickNewList()

    fun onClickBackButton()
    fun onClickShowDelete()
    fun onClickDelete(listId: Int, listName: String)
}