package com.elhady.movies.utilities

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "282157b63b2a2ef81abaca304a648cba"
    const val IMAGE_PATH = "https://image.tmdb.org/t/p/w500"
    const val ITEMS_PER_PAGE = 50
    const val MYSTERY_ID = 9648
    const val ADVENTURE_ID = 12


    fun <T> LifecycleOwner.collectLast(flow: Flow<T>, action: suspend (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(action)
            }
        }
    }
}