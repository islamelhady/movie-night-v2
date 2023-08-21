package com.elhady.movies.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.elhady.movies.data.Category
import com.elhady.movies.data.Movie
import com.elhady.movies.ui.base.BaseViewModel

@SuppressLint("SuspiciousIndentation")
class HomeViewModel : BaseViewModel() {

    val data2 = MutableLiveData<List<Movie>>()

    val data = MutableLiveData<List<Category>>()

    private val list = mutableListOf<Category>()


    init {

        for (i in 0..10)
            list.add(Category("Test $i ",i))
            val movies= mutableListOf(
                Movie("Movie 1", "2010"),
                Movie("Movie 2", "2011"),
                Movie("Movie 3", "2012"),
                Movie("Movie 4", "2013"),
                Movie("Movie 5", "2014"),
                Movie("Movie 6", "2015"),
                Movie("Movie 7", "2016")
            )
            data2.postValue(movies)
            data.postValue(list)
    }
}