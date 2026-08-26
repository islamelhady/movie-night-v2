package com.elhady.movies.feature.home.presentation.home.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewAiringTodayTvBinding
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewNowPlayingBinding
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewPopularMoviesBinding
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewPopularPeopleBinding
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewSliderBinding
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewTopRatedBinding
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewTrendingBinding
import com.elhady.movies.feature.home.databinding.HomeRecyclerviewTvShowsBinding
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.HomeItem
import java.lang.Math.abs

class HomeAdapter(
    private var items: List<HomeItem>,
    private val listener: HomeAdapterListener
) : BaseAdapter<HomeItem>(items, listener) {
    override val layoutID: Int = 0
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeItem.Slider -> SLIDER
            is HomeItem.NowPlaying -> NOW_PLAYING
            is HomeItem.TvShow -> TV_SHOW
            is HomeItem.AiringTodayTvShow -> AIRING_TODAY
            is HomeItem.TrendingMovie -> TRENDING
            is HomeItem.TopRatedMovie -> TOP_RATED
            is HomeItem.PopularPeople -> POPULAR_PEOPLE
            is HomeItem.PopularMovies -> POPULAR_MOVIES
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SLIDER -> {
                SliderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_slider, parent, false
                    )
                )
            }

            NOW_PLAYING -> {
                NowPlayingViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_now_playing, parent, false
                    )
                )
            }

            TV_SHOW -> {
                TvShowViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_tv_shows, parent, false
                    )
                )
            }

            AIRING_TODAY -> {
                AiringTodayTvShowViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_airing_today_tv, parent, false
                    )
                )
            }

            TRENDING -> {
                TrendingViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_trending, parent, false
                    )
                )
            }

            TOP_RATED -> {
                TopRatedViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_top_rated, parent, false
                    )
                )
            }

            POPULAR_PEOPLE -> {
                PopularPeopleViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_popular_people, parent, false
                    )
                )
            }

            POPULAR_MOVIES -> {
                PopularMoviesViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.home_recyclerview_popular_movies, parent, false
                    )
                )
            }


            else -> error("Unknown HomeItem view type: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        when (holder) {
            is SliderViewHolder -> bindSlider(holder, position)
            is NowPlayingViewHolder -> bindNowPlaying(holder, position)
            is TrendingViewHolder -> bindTrending(holder, position)
            is TopRatedViewHolder -> bindTopRated(holder, position)
            is PopularPeopleViewHolder -> bindPopularPeople(holder, position)
            is PopularMoviesViewHolder -> bindPopularMovies(holder, position)
            is TvShowViewHolder -> bindTvShow(holder, position)
            is AiringTodayTvShowViewHolder -> bindAiringTodayTvShow(holder, position)
        }
    }

    private fun bindSlider(
        holder: SliderViewHolder,
        position: Int
    ) {
        val upcomingSlider = items[position] as HomeItem.Slider
        val viewPager = holder.binding.viewPager
        val adapter = UpcomingMovieAdapter(upcomingItems = upcomingSlider.items, listener)
        setupViewPager(viewPager, adapter)
        registerPageChangeCallback(viewPager)
        setSliderPageTransformer(viewPager)
        holder.binding.apply {
            setVariable(BR.item, upcomingSlider)
        }
    }

    private fun bindNowPlaying(
        holder: NowPlayingViewHolder,
        position: Int
    ) {
        val nowPlaying = items[position] as HomeItem.NowPlaying
        val adapter = NowPlayingMovieAdapter(nowPlayingItems = nowPlaying.items, listener)
        holder.binding.apply {
            recyclerViewNowPlaying.adapter = adapter
            setVariable(BR.item, nowPlaying)
        }
    }

    private fun bindTvShow(
        holder: TvShowViewHolder,
        position: Int
    ) {
        val tvShow = items[position] as HomeItem.TvShow
        if (tvShow.items.isEmpty()) return
        holder.binding.apply {
            airingTodayTv = tvShow.items.first()
            topRatedTv = tvShow.items.first()
            popularTv = tvShow.items.last()
            onTheAirTv = tvShow.items.last()
            setVariable(BR.listener, this@HomeAdapter.listener)
            setVariable(BR.item, tvShow)
        }
    }

    private fun bindTopRated(holder: TopRatedViewHolder, position: Int) {
        val topRated = items[position] as HomeItem.TopRatedMovie
        val adapter = TopRatedMovieAdapter(
            itemsTopRated = topRated.items, listener
        )
        holder.binding.apply {
            recyclerViewTopRated.adapter = adapter
            setVariable(BR.item, topRated)
            setVariable(BR.listener, this@HomeAdapter.listener)
        }
    }

    private fun bindAiringTodayTvShow(holder: AiringTodayTvShowViewHolder, position: Int) {
        val airingToday = items[position] as HomeItem.AiringTodayTvShow
        val adapter =
            AiringTodayTvShowAdapter(itemsAiringToday = airingToday.items, listener = listener)
        holder.binding.apply {
            recyclerAiringTvShows.adapter = adapter
            count = airingToday.items.size
            setVariable(BR.item, airingToday)
            setVariable(BR.listener, this@HomeAdapter.listener)
        }
    }

    private fun bindTrending(
        holder: TrendingViewHolder,
        position: Int
    ) {
        val trending = items[position] as HomeItem.TrendingMovie
        val adapter = TrendingMovieAdapter(trending.items, listener)
        holder.binding.apply {
            recyclerViewTrending.adapter = adapter
            setVariable(BR.item, trending)
            setVariable(BR.listener, this@HomeAdapter.listener)
        }
    }

    private fun bindPopularPeople(
        holder: PopularPeopleViewHolder,
        position: Int
    ) {
        val popularPeople = items[position] as HomeItem.PopularPeople
        val adapter = PopularPeopleAdapter(
            popularPeople.items, listener
        )
        holder.binding.apply {
            recyclerViewPopularPeople.adapter = adapter
            setVariable(BR.item, popularPeople)
        }
    }

    private fun bindPopularMovies(
        holder: PopularMoviesViewHolder,
        position: Int
    ) {
        val popularMovies = items[position] as HomeItem.PopularMovies
        val adapter = PopularMoviesAdapter(popularMovies.items, listener)
        holder.binding.apply {
            recyclerViewPopularMovies.adapter = adapter
            setVariable(BR.item, popularMovies)
            setVariable(BR.listener, this@HomeAdapter.listener)
        }
    }

    private fun setupViewPager(
        viewPager: ViewPager2,
        adapter: RecyclerView.Adapter<*>
    ) {
        viewPager.apply {
            this.adapter = adapter
            offscreenPageLimit = 3
        }
    }

    private fun registerPageChangeCallback(viewPager: ViewPager2) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            viewPager.currentItem += 1
        }

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 6000)
                }
            }
        )
    }

    private fun setSliderPageTransformer(viewPager: ViewPager2) {
        val transformer = CompositePageTransformer()

        transformer.addTransformer(
            MarginPageTransformer(16)
        )

        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager.setPageTransformer(transformer)
    }


    class SliderViewHolder(
        val binding: HomeRecyclerviewSliderBinding
    ) : BaseViewHolder(binding)

    class NowPlayingViewHolder(
        val binding: HomeRecyclerviewNowPlayingBinding
    ) : BaseViewHolder(binding)

    class TvShowViewHolder(
        val binding: HomeRecyclerviewTvShowsBinding
    ) : BaseViewHolder(binding)

    class TrendingViewHolder(
        val binding: HomeRecyclerviewTrendingBinding
    ) : BaseViewHolder(binding)

    class AiringTodayTvShowViewHolder(
        val binding: HomeRecyclerviewAiringTodayTvBinding
    ) : BaseViewHolder(binding)

    class TopRatedViewHolder(
        val binding: HomeRecyclerviewTopRatedBinding
    ) : BaseViewHolder(binding)

    class PopularPeopleViewHolder(
        val binding: HomeRecyclerviewPopularPeopleBinding
    ) : BaseViewHolder(binding)

    class PopularMoviesViewHolder(
        val binding: HomeRecyclerviewPopularMoviesBinding
    ) : BaseViewHolder(binding)

    private fun HomeItem.order(): Int =
        when (this) {
            is HomeItem.Slider -> 0
            is HomeItem.NowPlaying -> 1
            is HomeItem.TvShow -> 2
            is HomeItem.AiringTodayTvShow -> 3
            is HomeItem.TrendingMovie -> 4
            is HomeItem.TopRatedMovie -> 5
            is HomeItem.PopularPeople -> 6
            is HomeItem.PopularMovies -> 7
        }

    fun setItem(item: HomeItem) {
        val newItems = items.toMutableList()

        val index = newItems.indexOfFirst {
            it::class == item::class
        }

        if (index == -1) {
            newItems.add(item)
        } else {
            newItems[index] = item
        }

        setItems(newItems)
    }

    override fun setItems(newItems: List<HomeItem>) {
        items = newItems
            .sortedBy { it.order() }
            .toMutableList()

        super.setItems(items)
    }
    private companion object {
        const val SLIDER = 0
        const val NOW_PLAYING = 1
        const val TV_SHOW = 2
        const val AIRING_TODAY = 3
        const val TRENDING = 4
        const val TOP_RATED = 5
        const val POPULAR_PEOPLE = 6
        const val POPULAR_MOVIES = 7
    }

}
