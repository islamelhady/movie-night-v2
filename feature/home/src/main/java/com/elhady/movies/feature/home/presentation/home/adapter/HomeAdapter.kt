package com.elhady.movies.feature.home.presentation.home.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
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
import kotlin.math.abs

class HomeAdapter(
    private val listener: HomeAdapterListener
) : ListAdapter<HomeItem, RecyclerView.ViewHolder>(HomeDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SLIDER -> {
                SliderViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_slider, parent, false),
                    listener
                )
            }
            NOW_PLAYING -> {
                NowPlayingViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_now_playing, parent, false),
                    listener
                )
            }
            TV_SHOW -> {
                TvShowViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_tv_shows, parent, false)
                )
            }
            AIRING_TODAY -> {
                AiringTodayTvShowViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_airing_today_tv, parent, false),
                    listener
                )
            }
            TRENDING -> {
                TrendingViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_trending, parent, false),
                    listener
                )
            }
            TOP_RATED -> {
                TopRatedViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_top_rated, parent, false),
                    listener
                )
            }
            POPULAR_PEOPLE -> {
                PopularPeopleViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_popular_people, parent, false),
                    listener
                )
            }
            POPULAR_MOVIES -> {
                PopularMoviesViewHolder(
                    DataBindingUtil.inflate(inflater, R.layout.home_recyclerview_popular_movies, parent, false),
                    listener
                )
            }
            else -> error("Unknown HomeItem view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is SliderViewHolder -> holder.bind(item as HomeItem.Slider)
            is NowPlayingViewHolder -> holder.bind(item as HomeItem.NowPlaying)
            is TrendingViewHolder -> holder.bind(item as HomeItem.TrendingMovie)
            is TopRatedViewHolder -> holder.bind(item as HomeItem.TopRatedMovie)
            is PopularPeopleViewHolder -> holder.bind(item as HomeItem.PopularPeople)
            is PopularMoviesViewHolder -> holder.bind(item as HomeItem.PopularMovies)
            is TvShowViewHolder -> holder.bind(item as HomeItem.TvShow, listener)
            is AiringTodayTvShowViewHolder -> holder.bind(item as HomeItem.AiringTodayTvShow)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is SliderViewHolder) {
            holder.release()
        }
    }

    class SliderViewHolder(
        val binding: HomeRecyclerviewSliderBinding,
        listener: HomeAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val handler = Handler(Looper.getMainLooper())
        private val runnable = Runnable { binding.viewPager.currentItem += 1 }
        private var callback: ViewPager2.OnPageChangeCallback? = null
        private val adapter = UpcomingMovieAdapter(listener)

        init {
            binding.viewPager.apply {
                this.adapter = this@SliderViewHolder.adapter
                offscreenPageLimit = 3
                setSliderPageTransformer(this)
            }
        }

        fun bind(upcomingSlider: HomeItem.Slider) {
            adapter.submitList(upcomingSlider.items)
            
            callback?.let { binding.viewPager.unregisterOnPageChangeCallback(it) }
            callback = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 6000)
                }
            }
            binding.viewPager.registerOnPageChangeCallback(callback!!)

            binding.setVariable(BR.item, upcomingSlider)
            binding.executePendingBindings()
        }

        private fun setSliderPageTransformer(viewPager: ViewPager2) {
            val transformer = CompositePageTransformer()
            transformer.addTransformer(MarginPageTransformer(16))
            transformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
            viewPager.setPageTransformer(transformer)
        }

        fun release() {
            handler.removeCallbacks(runnable)
            callback?.let { binding.viewPager.unregisterOnPageChangeCallback(it) }
            callback = null
        }
    }

    class NowPlayingViewHolder(
        val binding: HomeRecyclerviewNowPlayingBinding,
        listener: HomeAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = NowPlayingMovieAdapter(listener)
        init {
            binding.recyclerViewNowPlaying.adapter = adapter
        }
        fun bind(nowPlaying: HomeItem.NowPlaying) {
            adapter.submitList(nowPlaying.items)
            binding.setVariable(BR.item, nowPlaying)
            binding.executePendingBindings()
        }
    }

    class TvShowViewHolder(
        val binding: HomeRecyclerviewTvShowsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: HomeItem.TvShow, listener: HomeAdapterListener) {
            if (tvShow.items.isEmpty()) return
            binding.apply {
                airingTodayTv = tvShow.items.first()
                topRatedTv = tvShow.items.first()
                popularTv = tvShow.items.last()
                onTheAirTv = tvShow.items.last()
                setVariable(BR.listener, listener)
                setVariable(BR.item, tvShow)
                executePendingBindings()
            }
        }
    }

    class TrendingViewHolder(
        val binding: HomeRecyclerviewTrendingBinding,
        listener: HomeAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = TrendingMovieAdapter(listener)
        init {
            binding.recyclerViewTrending.adapter = adapter
        }
        fun bind(trending: HomeItem.TrendingMovie) {
            adapter.submitList(trending.items)
            binding.setVariable(BR.item, trending)
            binding.executePendingBindings()
        }
    }

    class AiringTodayTvShowViewHolder(
        val binding: HomeRecyclerviewAiringTodayTvBinding,
        listener: HomeAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = AiringTodayTvShowAdapter(listener)
        init {
            binding.recyclerAiringTvShows.adapter = adapter
        }
        fun bind(airingToday: HomeItem.AiringTodayTvShow) {
            adapter.submitList(airingToday.items)
            binding.count = airingToday.items.size
            binding.setVariable(BR.item, airingToday)
            binding.executePendingBindings()
        }
    }

    class TopRatedViewHolder(
        val binding: HomeRecyclerviewTopRatedBinding,
        listener: HomeAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = TopRatedMovieAdapter(listener)
        init {
            binding.recyclerViewTopRated.adapter = adapter
        }
        fun bind(topRated: HomeItem.TopRatedMovie) {
            adapter.submitList(topRated.items)
            binding.setVariable(BR.item, topRated)
            binding.executePendingBindings()
        }
    }

    class PopularPeopleViewHolder(
        val binding: HomeRecyclerviewPopularPeopleBinding,
        listener: HomeAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = PopularPeopleAdapter(listener)
        init {
            binding.recyclerViewPopularPeople.adapter = adapter
        }
        fun bind(popularPeople: HomeItem.PopularPeople) {
            adapter.submitList(popularPeople.items)
            binding.setVariable(BR.item, popularPeople)
            binding.executePendingBindings()
        }
    }

    class PopularMoviesViewHolder(
        val binding: HomeRecyclerviewPopularMoviesBinding,
        listener: HomeAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = PopularMoviesAdapter(listener)
        init {
            binding.recyclerViewPopularMovies.adapter = adapter
        }
        fun bind(popularMovies: HomeItem.PopularMovies) {
            adapter.submitList(popularMovies.items)
            binding.setVariable(BR.item, popularMovies)
            binding.executePendingBindings()
        }
    }

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
        val newItems = currentList.toMutableList()
        val index = newItems.indexOfFirst { it::class == item::class }
        if (index == -1) {
            newItems.add(item)
        } else {
            newItems[index] = item
        }
        submitList(newItems.sortedBy { it.order() })
    }

    fun submitHomeItems(newItems: List<HomeItem>?) {
        submitList(newItems?.sortedBy { it.order() })
    }

    class HomeDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem::class == newItem::class
        }
        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }
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
