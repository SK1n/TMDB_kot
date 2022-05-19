package com.example.tmdb.ui.moviesUpcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.tmdb.R
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.databinding.FragmentUpcomingMoviesBinding
import com.example.tmdb.ui.customViews.MarginDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UpcomingFragment : Fragment() {
    private val viewModel: UpcomingViewModel by viewModels()
    private var _binding: FragmentUpcomingMoviesBinding? = null
    private lateinit var pagerAdapter: MoviesAdapter
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupRecyclerView()
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter.onItemClick = {
            val bundle = bundleOf("movie" to it)
            navController.navigate(R.id.navigation_movie,bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pagerAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.isLoading.value = loadStates.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launch {
            viewModel.getData().collectLatest { pagerAdapter.submitData(it) }
        }
    }
    private fun setupRecyclerView() {
        pagerAdapter = MoviesAdapter()
        binding.upcomingRecycler.apply {
            adapter = pagerAdapter
            addItemDecoration(MarginDecoration(context))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
