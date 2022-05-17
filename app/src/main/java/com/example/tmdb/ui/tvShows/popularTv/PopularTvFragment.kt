package com.example.tmdb.ui.tvShows.popularTv


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
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.tmdb.R
import com.example.tmdb.adapters.TvShowsAdapter
import com.example.tmdb.databinding.FragmentTvShowsPopularBinding
import com.example.tmdb.widgets.MarginDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularTvFragment : Fragment() {
    private val viewModel: PopularTvViewModel by viewModels()
    private var _binding: FragmentTvShowsPopularBinding? = null
    private lateinit var pagerAdapter: TvShowsAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTvShowsPopularBinding.inflate(inflater, container, false)
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
            val bundle = bundleOf("tvShow" to it)

            navController.navigate(R.id.navigation_tv_shows_details, bundle)
            Log.d("PopularTv", "onViewCreated: Pressed $bundle")
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
        pagerAdapter = TvShowsAdapter()
        binding.popularTvRecycler.apply {
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