package com.elhady.movies.presentation.ui.tvdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.databinding.ItemCommentBinding
import com.elhady.movies.databinding.ItemSeasonHorizontalBinding
import com.elhady.movies.databinding.TvDetailsItemPeopleRvBinding
import com.elhady.movies.databinding.TvDetailsItemRecommendedRvBinding
import com.elhady.movies.databinding.TvDetailsItemUpperBinding
import com.elhady.movies.presentation.ui.common.adapters.PeopleAdapter
import com.elhady.movies.presentation.ui.tvdetails.TvDetailsItem
import com.elhady.movies.presentation.ui.tvdetails.TvDetailsType
import com.elhady.movies.presentation.viewmodel.tvdetails.listener.TvDetailsListeners

class TvDetailsAdapter(
    private var tvDetailsItems: MutableList<TvDetailsItem>,
    private val listener: TvDetailsListeners
) : BaseAdapter<TvDetailsItem>(tvDetailsItems, listener) {
    override val layoutID: Int = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TvDetailsType.UPPER.ordinal -> UpperViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.tv_details_item_upper, parent, false
                )
            )

            TvDetailsType.PEOPLE.ordinal -> PeopleViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.tv_details_item_people_rv, parent, false
                )
            )

            TvDetailsType.Seasons.ordinal -> SeasonViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_season_horizontal, parent, false
                )
            )

            TvDetailsType.RECOMMENDED.ordinal -> RecommendedViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.tv_details_item_recommended_rv, parent, false
                )
            )

            TvDetailsType.REVIEWS.ordinal -> ReviewViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_comment, parent, false
                )
            )

            else -> throw IllegalStateException("Unkown ID ${parent}")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is UpperViewHolder -> bindUpper(holder, position)
            is PeopleViewHolder -> bindPeople(holder, position)
            is SeasonViewHolder -> bindSeason(holder, position)
            is RecommendedViewHolder -> bindRecommended(holder, position)
            is ReviewViewHolder -> bindReview(holder, position)
        }
    }


    override fun setItems(newItems: List<TvDetailsItem>) {
        tvDetailsItems = newItems.sortedBy { it.type.ordinal }.toMutableList()
        super.setItems(newItems)
    }

    override fun getItemViewType(position: Int): Int = tvDetailsItems[position].type.ordinal

    private fun bindUpper(holder: UpperViewHolder, position: Int) {
        val upper = tvDetailsItems[position] as TvDetailsItem.Upper
        holder.binding.item = upper
        holder.binding.playButtonListener = listener
        holder.binding.rateListener = listener
    }

    private fun bindPeople(holder: PeopleViewHolder, position: Int) {
        val people = tvDetailsItems[position] as TvDetailsItem.People
        val adapter = PeopleAdapter(people.people, listener)
        holder.binding.listener = listener
        holder.binding.recyclerViewPeople.adapter = adapter
        holder.binding.item = people
    }

    private fun bindSeason(holder: SeasonViewHolder, position: Int) {
        val season = tvDetailsItems[position] as TvDetailsItem.Season
        holder.binding.item = season.season
        holder.binding.listener = listener
    }

    private fun bindRecommended(holder: RecommendedViewHolder, position: Int) {
        val recommendedItems = tvDetailsItems[position] as TvDetailsItem.Recommended
        val adapter = RecommendedAdapter(recommendedItems.recommended, listener)
        holder.binding.listener = listener
        holder.binding.recyclerViewRecommended.adapter = adapter
        holder.binding.items = recommendedItems
    }

    private fun bindReview(holder: ReviewViewHolder, position: Int) {
        val review = tvDetailsItems[position] as TvDetailsItem.Review
        holder.binding.item = review.review
    }


    class UpperViewHolder(val binding: TvDetailsItemUpperBinding) : BaseViewHolder(binding)

    class PeopleViewHolder(val binding: TvDetailsItemPeopleRvBinding) : BaseViewHolder(binding)

    class SeasonViewHolder(val binding: ItemSeasonHorizontalBinding) : BaseViewHolder(binding)

    class RecommendedViewHolder(val binding: TvDetailsItemRecommendedRvBinding) : BaseViewHolder(binding)

    class ReviewViewHolder(val binding: ItemCommentBinding) : BaseViewHolder(binding)
}
