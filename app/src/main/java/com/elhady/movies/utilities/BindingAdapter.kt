package com.elhady.movies.utilities

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import coil.load
import com.elhady.movies.R
import com.elhady.movies.data.remote.State
import com.elhady.movies.ui.base.BaseAdapter

@BindingAdapter("app:movieImage")
fun bindMovieImage(image: ImageView, imageURL: String?) {
    imageURL?.let {
        image.load(imageURL) {
            error(R.drawable.ic_launcher_background)
        }
    }
}

@BindingAdapter(value = ["app:items"])
fun<T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItem(items ?: emptyList())
}

@BindingAdapter(value = ["app:showWhenLoading"])
fun <T> showWhenLoading(view: View, state: State<T>?) {
    if(state is State.Loading){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter(value = ["app:hideWhenLoading"])
fun <T> hideWhenLoading(view: View, state: State<T>?) {
    if (state is State.Success){
        view.visibility = View.GONE
    }else{
        view.visibility = View.VISIBLE
    }
}