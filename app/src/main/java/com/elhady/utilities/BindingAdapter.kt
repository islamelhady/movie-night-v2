package com.elhady.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.elhady.movies.R

@BindingAdapter("movieImage")
fun bindMovieImage(image: ImageView, imageURL: String?) {
    imageURL?.let {
        image.load(imageURL) {
            error(R.drawable.ic_launcher_background)
        }
    }
}