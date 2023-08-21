package com.elhady.movies.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.elhady.movies.R
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