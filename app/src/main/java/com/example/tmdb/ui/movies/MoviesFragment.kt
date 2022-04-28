package com.example.tmdb.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdb.databinding.FragmentMoviesBinding
import com.google.android.material.tabs.TabLayoutMediator

class MoviesFragment : Fragment() {
    var tabTitles =arrayOf("Now playing","Popular","Upcoming")
    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val pager2 = binding.moviesPager
        val tabLayout = binding.moviesTabLayout
        pager2.adapter = MoviesViewPagerAdapter(childFragmentManager,lifecycle)
        TabLayoutMediator(tabLayout,pager2) {
            tab, position -> tab.text =tabTitles[position]
        }.attach()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}