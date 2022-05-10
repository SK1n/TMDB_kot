package com.example.tmdb.ui.movies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdb.ui.movies.nowPlaying.NowPlayingFragment
import com.example.tmdb.ui.movies.popular.PopularFragment
import com.example.tmdb.ui.movies.upcoming.UpcomingFragment

class MoviesViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowPlayingFragment()
            1 -> PopularFragment()
            2 -> UpcomingFragment()
            else -> NowPlayingFragment()
        }
    }
}