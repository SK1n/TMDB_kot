package com.example.tmdb.ui.tvShows.topRatedTv

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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.adapters.TvShowsAdapter
import com.example.tmdb.databinding.FragmentTvShowsTopRatedBinding
import com.example.tmdb.utils.Constants
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopRatedTvFragment : Fragment() {
    private val viewModel: TopRatedTvViewModel by viewModels()
    private var _binding: FragmentTvShowsTopRatedBinding? = null
    private lateinit var tAdapter: TvShowsAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTvShowsTopRatedBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch { viewModel.getData().collectLatest { tAdapter.submitData(it) } }
    }

    private fun setupRecyclerView() {
        tAdapter = TvShowsAdapter()
        binding.topRatedTvRecycler.apply {
            adapter = tAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        viewModel.isLoading.value = false
    }

    private fun showProgressBar() {
        viewModel.isLoading.value = true
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