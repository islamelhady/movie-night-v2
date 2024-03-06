package com.elhady.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.ui.home.HomeItem
import com.elhady.viewmodel.showMore.ShowMoreType
import com.elhady.viewmodel.home.homeUiState.HomeListener
import com.elhady.viewmodel.home.homeUiState.MoviesUiState

class HomeAdapter(
    private var homeItems: List<HomeItem>,
    private val listener: BaseInteractionListener
) :
    BaseAdapter<HomeItem>(homeItems, listener) {
    override val layoutID: Int = 0

    override fun setItems(newItems: List<HomeItem>) {
        homeItems = newItems.sortedBy {
            it.priority
        }.toMutableList()
        super.setItems(newItems)
    }

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
            is HomeItem.PopularMovieSlider -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    PopularMovieAdapter(currentHomeItem.items, listener as HomeListener)
                )
            }

            is HomeItem.UpcomingMovies -> {
                bindMovie(holder, currentHomeItem.items, currentHomeItem.type)
            }

            is HomeItem.TrendingMovies -> {
               bindMovie(holder, currentHomeItem.items, currentHomeItem.type)
            }

            is HomeItem.NowPlayingMovies -> {
               holder.binding.setVariable(BR.adapterRecycler, NowPlayingAdapter(currentHomeItem.items, listener as HomeListener))
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.TopRatedMovies -> {
              bindMovie(holder, currentHomeItem.items, currentHomeItem.type)
            }

            is HomeItem.OnTheAirTvShows -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    OnTheAirAdapter(currentHomeItem.items, listener as HomeListener)
                )
                holder.binding.setVariable(BR.movieType, currentHomeItem.type)
            }

            is HomeItem.AiringTodayTvShows -> {
                holder.binding.setVariable(
                    BR.adapterRecycler,
                    AiringTodayAdapter(
                        currentHomeItem.items.take(6),
                        listener as HomeListener
                    )
                )
                holder.binding.setVariable(BR.count, currentHomeItem.items.size)
            }

            is HomeItem.TvShowsLists -> {
                holder.binding.run {
                    if (currentHomeItem.items.isNotEmpty()) {
                        holder.binding.setVariable(BR.topRated, currentHomeItem.items.first())
                        holder.binding.setVariable(BR.popular, currentHomeItem.items[1])
                        holder.binding.setVariable(BR.latest, currentHomeItem.items.last())
                        holder.binding.setVariable(BR.listener, listener as HomeListener)
                    }
                }
            }

            is HomeItem.MysteryMovies -> {
                bindMovie(holder, currentHomeItem.items, currentHomeItem.type)
            }

            is HomeItem.AdventureMovies -> {
                bindMovie(holder,currentHomeItem.items, currentHomeItem.type)
            }

            is HomeItem.PopularActor -> {
                holder.binding.setVariable(BR.adapterRecycler, PopularActorAdapter(currentHomeItem.items, listener as HomeListener))
                holder.binding.setVariable(BR.listener, listener)
            }
        }
    }

    private fun bindMovie(holder: ItemViewHolder, items: List<MoviesUiState>, type: ShowMoreType){
        holder.binding.run {
            setVariable(BR.adapterRecycler, MovieAdapter(items = items, listener = listener as HomeListener))
            setVariable(BR.movieType, type)
        }
    }

    override fun areItemContent(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem.priority == newItem.priority
    }

    override fun getItemViewType(position: Int): Int {
        return when (homeItems[position]) {
            is HomeItem.PopularMovieSlider -> R.layout.home_recyclerview_popular_movie_slider
            is HomeItem.AiringTodayTvShows -> R.layout.home_recyclerview_list_airing_today_tv_shows
            is HomeItem.TvShowsLists -> R.layout.home_recyclerview_list_tv_shows
            is HomeItem.PopularActor -> R.layout.home_recyclerview_popular_actors
            is HomeItem.OnTheAirTvShows -> R.layout.home_recyclerview_list_on_the_air_tv_shows
            is HomeItem.NowPlayingMovies -> R.layout.home_recyclerview_now_playing_tv_shows
            is HomeItem.UpcomingMovies,
            is HomeItem.TrendingMovies,
            is HomeItem.TopRatedMovies,
            is HomeItem.MysteryMovies,
            is HomeItem.AdventureMovies,
            -> R.layout.home_recyclerview_list_movies
        }
    }


}