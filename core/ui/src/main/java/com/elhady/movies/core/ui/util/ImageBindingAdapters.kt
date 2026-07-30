package com.elhady.movies.core.ui.util

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.elhady.movies.core.ui.R

@BindingAdapter(value = ["app:imageUrl"])
fun ImageView.loadImage(imageUrl: String?) {
    // Placeholder for movie_image and dots_loading
    val imageLink = if (imageUrl == null || imageUrl.contains("null"))
        android.R.drawable.ic_menu_gallery  else imageUrl

    Glide.with(context)
        .load(imageLink)
        .centerCrop()
        .error(android.R.drawable.stat_notify_error)
        .into(this)
}

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("ResourceAsColor")
@BindingAdapter(value = ["app:imageUri"], requireAll = false)
fun ImageView.loadImageWithPlaceholderColor(imageUri: String?) {
    if (imageUri != null) {
        Glide.with(context)
            .load(imageUri)
            .into(this)
    } else {
        this.let {
            this.setBackgroundColor(context.getColor(R.color.background))
        }
    }
}

@BindingAdapter(value = ["app:profileUrl"])
fun ImageView.loadProfileImage(profileUrl: String?) {
    val imageLink = if (profileUrl == null || profileUrl.contains("null"))
        DEFAULT_PROFILE_IMAGE else profileUrl

    Glide.with(context)
        .load(imageLink)
        .centerCrop()
        .error(android.R.drawable.stat_notify_error)
        .into(this)
}

private const val DEFAULT_PROFILE_IMAGE = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
