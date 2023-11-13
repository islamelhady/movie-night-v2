package com.elhady.movies.utilities

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.elhady.movies.R
import com.elhady.movies.data.remote.State
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.category.CategoryGenreUiState
import com.google.android.material.chip.ChipGroup

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

@BindingAdapter(value = ["showWhenListNotEmpty"])
fun<T> showWhenListNotEmpty(view: View, list: List<T>){
    view.isVisible = list.isNotEmpty() == true
}

@BindingAdapter(value = ["showWhenTextNotEmpty"])
fun showWhenTextNotEmpty(view: View, text: String?){
    view.isVisible = text?.isNotEmpty() == true
}

@BindingAdapter(value = ["overviewText"])
fun overviewText(view: TextView, text: String){
    if(text.isNotEmpty()){
        view.text = text
    }else{
        view.text = view.context.getString(R.string.empty_overview_text)
    }
}

@BindingAdapter("app:convertToHoursPattern")
fun convertToHoursPattern(view: TextView, duration: Int) {
    duration.let {
        val hours = (duration / 60).toString()
        val minutes = (duration % 60).toString()
        if (hours == "0") {
            view.text = view.context.getString(R.string.minutes_pattern, minutes)
        } else if (minutes == "0") {
            view.text = view.context.getString(R.string.hours_pattern, hours)
        } else {
            view.text = view.context.getString(R.string.hours_minutes_pattern, hours, minutes)
        }
    }
}



@BindingAdapter("app:chipsList", "app:listener", "app:selectedChip")
fun <T> setGenresChips(view: ChipGroup, chipList: List<CategoryGenreUiState>?, listener: T, selectedChip: Int?) {
    chipList?.let {
        it.forEach { genreItem ->
            view.addView(view.createChip(genreItem, listener))
        }
    }
    val index = chipList?.indexOf(chipList.find { it.id == selectedChip }) ?: Constants.FIRST_CATEGORY_ID
    view.getChildAt(index)?.id?.let {
        view.check(it)
    }
}

@BindingAdapter(value = ["app:searchInput", "app:searchLoading", "app:searchError"])
fun <T> hideWhenSearchSuccess(view: View, searchInput:String, error: List<T>, loading: Boolean){
    view.visibility = if (searchInput.isNotBlank() && error.isNullOrEmpty() && !loading){
        View.VISIBLE
    }else{
        View.INVISIBLE
    }
}

