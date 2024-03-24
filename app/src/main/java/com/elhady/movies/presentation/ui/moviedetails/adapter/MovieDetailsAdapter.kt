package com.elhady.movies.presentation.ui.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.databinding.ItemReviewBinding
import com.elhady.movies.databinding.MovieDetailsItemPopularPeopleBinding
import com.elhady.movies.databinding.MovieDetailsItemRecommendedBinding
import com.elhady.movies.databinding.MovieDetailsItemUpperBinding
import com.elhady.movies.presentation.ui.common.adapters.MediaVerticalAdapter
import com.elhady.movies.presentation.ui.common.adapters.PeopleAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.MediaListener
import com.elhady.movies.presentation.viewmodel.common.listener.PeopleListener
import com.elhady.movies.presentation.viewmodel.moviedetails.MovieDetailsListener

class MovieDetailsAdapter(
    private var itemsMovie: MutableList<MovieDetailsItem>,
    private val listener: MovieDetailsListener,
    private val movieListener: MediaListener,
    private val peopleListener: PeopleListener,
) : BaseAdapter<MovieDetailsItem>(itemsMovie, listener) {
    override val layoutID: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            MovieDetailsType.UPPER.ordinal->{
                UpperViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.movie_details_item_upper, parent, false
                    )
                )
            }
            MovieDetailsType.PEOPLE.ordinal -> {
                PeopleViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.movie_details_item_popular_people, parent, false
                    )
                )
            }

            MovieDetailsType.RECOMMENDED.ordinal -> {
                RecommendedViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.movie_details_item_recommended, parent, false
                    )
                )
            }

            MovieDetailsType.REVIEWS.ordinal -> {
                ReviewsViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_review, parent, false
                    )
                )
            }


            else -> throw Exception("Mimo")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is UpperViewHolder -> bindUpper(holder , position)
            is PeopleViewHolder -> bindPeople(holder, position)
            is RecommendedViewHolder -> bindRecommended(holder, position)
            is ReviewsViewHolder -> bindReviews(holder, position)
        }
    }

    fun setItem(item: MovieDetailsItem) {
        val newItems = itemsMovie.apply {
            removeAt(item.type.ordinal)
            add(item.type.ordinal, item)
        }
        setItems(newItems)
    }

    override fun setItems(newItems: List<MovieDetailsItem>) {
        itemsMovie = newItems.sortedBy { it.type.ordinal }.toMutableList()
        super.setItems(newItems)
    }

    private fun bindUpper(holder: UpperViewHolder, position: Int) {
        val upper = itemsMovie[position] as MovieDetailsItem.Upper
        holder.binding.item = upper
        holder.binding.listener = listener
    }
    private fun bindPeople(holder: PeopleViewHolder, position: Int) {
        val people = itemsMovie[position] as MovieDetailsItem.People
        val adapter = PeopleAdapter(people.list!! ,peopleListener)
        holder.binding.recyclerViewPeople.adapter = adapter
        holder.binding.item = people
    }

    private fun bindRecommended(holder: RecommendedViewHolder, position: Int) {
        val recommended = itemsMovie[position] as MovieDetailsItem.Recommended
        val adapter = MediaVerticalAdapter(recommended.list!!, movieListener)
        holder.binding.recyclerViewRecommened.adapter = adapter
        holder.binding.item = recommended
        holder.binding.listener = listener
    }

    private fun bindReviews(holder: ReviewsViewHolder, position: Int) {
        val review = itemsMovie[position] as MovieDetailsItem.Reviews
        holder.binding.item = review.list
    }


    override fun getItemViewType(position: Int): Int = itemsMovie[position].type.ordinal

    class UpperViewHolder(val binding: MovieDetailsItemUpperBinding) :
        BaseViewHolder(binding)
    class PeopleViewHolder(val binding: MovieDetailsItemPopularPeopleBinding) :
        BaseViewHolder(binding)

    class RecommendedViewHolder(val binding: MovieDetailsItemRecommendedBinding) :
        BaseViewHolder(binding)

    class ReviewsViewHolder(val binding: ItemReviewBinding) : BaseViewHolder(binding)


}