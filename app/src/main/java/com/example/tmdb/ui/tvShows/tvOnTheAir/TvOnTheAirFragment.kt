package com.example.tmdb.ui.tvShows.tvOnTheAir

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
import com.example.tmdb.adapters.TvShowsAdapter
import com.example.tmdb.databinding.FragmentTvShowsTvOnTheAirBinding
import com.example.tmdb.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.example.tmdb.utils.Resource

class TvOnTheAirFragment: Fragment() {
    private val viewModel: TvOnTheAirViewModel by viewModels()
    private var _binding: FragmentTvShowsTvOnTheAirBinding? = null
    private lateinit var tAdapter: TvShowsAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTvShowsTvOnTheAirBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        setupRecyclerView()
        viewModel.tvShowPage.observe(viewLifecycleOwner, Observer {
                response -> when(response) {
            is  Resource.Success -> {
                hideProgressBar()
                response.data?.let {
                        response ->
                    tAdapter.differ.submitList(response.results)
                    val totalPages = response.total_pages / QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.tvShowPageNumber == totalPages
                }
            }
            is Resource.Error -> {
                hideProgressBar()
                response.message?.let { message ->
                    Log.d("TotaFragment", "An error occured: $message")
                }
            }
            is Resource.Loading -> {
                showProgressBar()
            }
        }
        })
        return binding.root
    }

    private fun hideProgressBar() {
        viewModel.isLoading.value = false
    }
    private fun showProgressBar() {
        viewModel.isLoading.value = true
    }
    var isLastPage = false;
    var isScrolling = false;

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = viewModel.isLoading.value == false && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getPage()
                isScrolling = false
            } else {
                binding.totaRecycler.setPadding(0,0,0,0)
            }
        }
    }

    private fun setupRecyclerView() {
        tAdapter = TvShowsAdapter()
        binding.totaRecycler.apply {
            adapter = tAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@TvOnTheAirFragment.scrollListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}