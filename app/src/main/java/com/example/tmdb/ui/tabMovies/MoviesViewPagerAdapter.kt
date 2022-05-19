package com.example.tmdb.ui.tabMovies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdb.ui.moviesNowPlaying.NowPlayingFragment
import com.example.tmdb.ui.moviesPopular.PopularMoviesFragment
import com.example.tmdb.ui.moviesUpcoming.UpcomingFragment
import com.example.tmdb.utils.Constants.POSITION_ONE
import com.example.tmdb.utils.Constants.POSITION_TWO
import com.example.tmdb.utils.Constants.POSITION_ZERO
import com.example.tmdb.utils.Constants.TOTAL_ADAPTER_POSITIONS

class MoviesViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TOTAL_ADAPTER_POSITIONS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_ZERO -> NowPlayingFragment()
            POSITION_ONE -> PopularMoviesFragment()
            POSITION_TWO -> UpcomingFragment()
            else -> NowPlayingFragment()
        }
    }
}