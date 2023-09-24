package com.elhady.movies.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel(), MovieInteractionListener {

    private val _popularMovies = MutableLiveData<State<BaseResponse<MovieDto>>>()
    val popularMovie: LiveData<State<BaseResponse<MovieDto>>>
        get() = _popularMovies

    fun getPopular() {
        viewModelScope.launch {
            try {
                movieRepository.getPopularMovies().collect {

                    _popularMovies.postValue(it)
                }
            }catch (e: Exception){
                Log.d("ViewModel", e.message.toString())
            }
        }
    }




    init {
        getPopular()
    }

    override fun onClickMovie(name: String) {
        TODO("Not yet implemented")
    }


}