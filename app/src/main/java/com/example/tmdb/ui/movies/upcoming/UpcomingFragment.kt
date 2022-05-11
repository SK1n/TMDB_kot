package com.example.tmdb.ui.movies.upcoming

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.databinding.FragmentUpcomingMoviesBinding
import com.example.tmdb.utils.Constants
import com.example.tmdb.utils.Resource

class UpcomingFragment : Fragment() {
    private val viewModel: UpcomingViewModel by viewModels()
    private var _binding: FragmentUpcomingMoviesBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupRecyclerView()
        return binding.root
    }
    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.upcomingRecycler.apply {
            adapter = moviesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
