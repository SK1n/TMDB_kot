package com.example.tmdb.ui.tvShows

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdb.ui.tvShows.popularTv.PopularTvFragment
import com.example.tmdb.ui.tvShows.topRatedTv.TopRatedTvFragment
import com.example.tmdb.ui.tvShows.tvOnTheAir.TvOnTheAirFragment

class TvShowsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TvOnTheAirFragment()
            1 -> PopularTvFragment()
            3 -> TopRatedTvFragment()
            else -> TvOnTheAirFragment()
        }
    }
}