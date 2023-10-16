package com.elhady.movies.utilities

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import coil.load
import com.elhady.movies.R
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.ui.base.BaseAdapter
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("app:movieImage")
fun bindMovieImage(image: ShapeableImageView, imageURL: String?) {
    imageURL?.let {
        image.load(imageURL) {
            error(R.drawable.ic_launcher_background)
        }
    }
}

@BindingAdapter(value = ["app:items"])
fun<T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
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

@BindingAdapter(value = ["hideIfTrue"])
fun hideIfTrue(view: View, visible: Boolean){
    view.isVisible = !visible
}

@BindingAdapter(value = ["isVisible"])
fun isVisible(view: View, visible: Boolean){
    view.isVisible = visible
}

@BindingAdapter(value = ["genre"])
fun setAllGenre(textView: TextView, genreList: List<String>?){
    genreList?.let {
        textView.text = genreList.joinToString(".") {
            it
        }
    }
}

@BindingAdapter(value = ["app:usePagerSnapHelper"])
fun usePagerSnapHelperWithRecycler(recycler: RecyclerView, useSnapHelper: Boolean = false) {
    if (useSnapHelper)
        PagerSnapHelper().attachToRecyclerView(recycler)
}