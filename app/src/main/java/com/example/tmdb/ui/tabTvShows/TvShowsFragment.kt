package com.example.tmdb.ui.tabTvShows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdb.databinding.FragmentTabTvShowsBinding
import com.google.android.material.tabs.TabLayoutMediator

class TvShowsFragment : Fragment() {
    var tabTitles = arrayOf("Tv On The Air", "Popular", "Top Rated")
    private var _binding: FragmentTabTvShowsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabTvShowsBinding.inflate(inflater, container, false)
        val pager2 = binding.tvShowsPager
        val tabLayout = binding.tvShowsTabLayout
        pager2.adapter = TvShowsViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, pager2) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}