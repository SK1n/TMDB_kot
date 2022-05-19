package com.example.tmdb.ui.tabTvShows

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdb.ui.tvOnTheAir.TvOnTheAirFragment
import com.example.tmdb.ui.tvPopular.PopularTvFragment
import com.example.tmdb.ui.tvTopRated.TopRatedTvFragment
import com.example.tmdb.utils.Constants.POSITION_ONE
import com.example.tmdb.utils.Constants.POSITION_TWO
import com.example.tmdb.utils.Constants.POSITION_ZERO
import com.example.tmdb.utils.Constants.TOTAL_ADAPTER_POSITIONS

class TvShowsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TOTAL_ADAPTER_POSITIONS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_ZERO -> TvOnTheAirFragment()
            POSITION_ONE -> PopularTvFragment()
            POSITION_TWO -> TopRatedTvFragment()
            else -> TvOnTheAirFragment()
        }
    }
}