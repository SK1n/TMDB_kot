package com.example.tmdb.ui.movies.nowPlaying

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.tmdb.R
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.databinding.FragmentNowPlayingMoviesBinding
import com.example.tmdb.ui.movies.MoviesFragmentDirections
import com.example.tmdb.widgets.MarginDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NowPlayingFragment : Fragment() {
    private val viewModel: NowPlayingViewModel by viewModels()
    private var _binding: FragmentNowPlayingMoviesBinding? = null
    private lateinit var pagerAdapter: MoviesAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNowPlayingMoviesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
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
        binding.nowPlayingRecycler.apply {
            adapter = pagerAdapter
            addItemDecoration(MarginDecoration(context))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
