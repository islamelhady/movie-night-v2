package com.elhady.movies.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elhady.movies.data.remote.repository.MovieRepository
import com.elhady.movies.data.remote.test.Category
import com.elhady.movies.data.remote.test.Movie
import com.elhady.movies.ui.home.adapters.BannerInteractionListener
import com.elhady.movies.ui.home.adapters.CategoryInteractionListener
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel(), CategoryInteractionListener, MovieInteractionListener, BannerInteractionListener {

    val data2 = MutableLiveData<List<Movie>>()

    val data = MutableLiveData<List<Category>>()

    private val list = mutableListOf<Category>()

    init {
        for (i in 0..10)
            list.add(Category("TEST $i", i))
        val movies = mutableListOf(
            Movie("Test 1", ""),
            Movie("Test 2", ""),
            Movie("Test 3", ""),
            Movie("Test 4", ""),
            Movie("Test 5", ""),
            Movie("Test 6", "")
        )
        data2.postValue(movies)
        data.postValue(list)
    }

    override fun onClickCategory(name: String) {
        Log.e("TEST", name)
    }

    override fun onClickMovie(name: String) {
        Log.e("TEST", name)
    }

    fun seeAllCategory() {
        Log.e("TEST", "All category")
    }

    fun seeAllMovie() {
        Log.e("TEST", "All Movie")
    }

    override fun onClickBanner(name: String) {
        Log.e("TEST", "All banner")
    }
}