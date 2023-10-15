package com.elhady.movies.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.home.HomeItem

class HomeAdapter(
    private var homeItems: List<HomeItem>,
    private val listener: BaseInteractionListener
) :
    BaseAdapter<HomeItem>(homeItems, listener) {
    override val layoutID: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder as ItemViewHolder, position)
    }

    private fun bind(holder: ItemViewHolder, position: Int) {
        when (val currentHomeItem = homeItems[position]) {
            is HomeItem.Slider -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    PopularMovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
            }

            is HomeItem.Upcoming -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    MovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.Trending -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    MovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.NowPlaying -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    MovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.TopRated -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    MovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.OnTheAirSeries -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    MovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.AiringTodaySeries -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    AiringTodayAdapter(
                        currentHomeItem.items.take(6),
                        listener as AiringTodayInteractionListener
                    )
                )
                holder.binding.setVariable(BR.count, currentHomeItem.items.size)
            }

            is HomeItem.TVSeriesLists -> {
                holder.binding.run {
                    if (currentHomeItem.items.isNotEmpty()) {
                        holder.binding.setVariable(BR.topRated, currentHomeItem.items.first())
                        holder.binding.setVariable(BR.popular, currentHomeItem.items[1])
                        holder.binding.setVariable(BR.latest, currentHomeItem.items.last())
                    }
                }
            }

            is HomeItem.Mystery -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    MovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.Adventure -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    MovieAdapter(currentHomeItem.items, listener as MovieInteractionListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.Actor -> {
                holder.binding.setVariable(BR.adapterRecycler, ActorAdapter(currentHomeItem.items,listener as ActorInteractionListener, R.layout.item_actor))
            }
        }
    }

    override fun setItems(newItems: List<HomeItem>) {
        homeItems = newItems.toMutableList()
        super.setItems(newItems)
    }


    override fun areItemContent(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int {
        return when (homeItems[position]) {
            is HomeItem.Slider -> R.layout.list_popular_movie
            is HomeItem.AiringTodaySeries -> R.layout.list_airing_today
            is HomeItem.TVSeriesLists -> R.layout.list_tv_series
            is HomeItem.Actor -> R.layout.list_actors
            is HomeItem.Upcoming,
            is HomeItem.Trending,
            is HomeItem.NowPlaying,
            is HomeItem.TopRated,
            is HomeItem.OnTheAirSeries,
            is HomeItem.Mystery,
            is HomeItem.Adventure,
            -> R.layout.list_movie
        }
    }


}