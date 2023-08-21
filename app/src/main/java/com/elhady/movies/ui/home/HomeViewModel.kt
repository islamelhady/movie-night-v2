package com.elhady.movies.ui.home

import androidx.lifecycle.MutableLiveData
import com.elhady.movies.data.Movie
import com.elhady.movies.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    val data = mutableListOf<Movie>(
        Movie("Movie 1", "2010"),
        Movie("Movie 2", "2011"),
        Movie("Movie 3", "2012"),
        Movie("Movie 4", "2013"),
        Movie("Movie 5", "2014"),
        Movie("Movie 6", "2015"),
        Movie("Movie 7", "2016")
    )

    private val list = mutableListOf<String>()
    val data2 = MutableLiveData<List<String>>()

    init {
        for (i in 0..10){
            list.add("Test $i")
            data2.postValue(list)
        }
    }
}