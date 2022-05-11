package com.example.tmdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.widgets.MarginDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var pagerAdapter: MoviesAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = view.findNavController()
        pagerAdapter.onItemClick = {
            var opt = HomeFragmentDirections.actionNavigationHomeToNavigationMovie(it)
            navController.navigate(opt)
            Log.d("HomeFragment", "onViewCreated: $it")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            pagerAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.isLoading.value = loadStates.refresh is LoadState.Loading

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getData().collectLatest { pagerAdapter.submitData(it) }
        }
    }

    private fun setupRecyclerView() {
        pagerAdapter = MoviesAdapter()
        binding.topRatedRecycler.apply {
            adapter = pagerAdapter
            addItemDecoration(MarginDecoration(context))
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}