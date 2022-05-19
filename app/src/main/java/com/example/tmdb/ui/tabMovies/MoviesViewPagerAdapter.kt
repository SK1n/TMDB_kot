package com.example.tmdb.ui.tabMovies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdb.ui.moviesNowPlaying.NowPlayingFragment
import com.example.tmdb.ui.moviesPopular.PopularMoviesFragment
import com.example.tmdb.ui.moviesUpcoming.UpcomingFragment

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
            1 -> PopularMoviesFragment()
            2 -> UpcomingFragment()
            else -> NowPlayingFragment()
        }
    }
}