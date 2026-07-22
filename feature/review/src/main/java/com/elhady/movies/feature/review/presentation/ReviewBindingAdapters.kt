package com.elhady.movies.feature.review.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["app:imageUrlForReviews", "app:autherName"])
fun ImageView.loadImageForReviews(backDropPath: String?, autherName: String?) {
    if (!backDropPath.isNullOrEmpty()) {
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500" + backDropPath)
            .fitCenter()
            .centerCrop()
            .into(this)
    }
}
