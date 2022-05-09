package com.example.tmdb.ui.home

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.utils.Resource


class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var mAdapter: MoviesAdapter
    private var  page: Int = 1
    var TAG = "HomeFragment"
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
        viewModel.moviesPage.observe(viewLifecycleOwner, Observer {
            response -> when(response) {
                is  Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        moviesResponse ->
                        mAdapter.differ.submitList(moviesResponse.results.toList())
                        val totalPages = moviesResponse.total_pages / 20 + 2
                        isLastPage = viewModel.moviesPageNumber == totalPages
                    }
                }
            is Resource.Error -> {
                hideProgressBar()
                response.message?.let { message ->
                    Log.d(TAG, "An error occured: $message")
                }
            }
            is Resource.Loading -> {
                showProgressBar()
            }
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun hideProgressBar() {
        binding.topRatedProgress.visibility = View.INVISIBLE
        isLoading = false
    }
    private fun showProgressBar() {
        binding.topRatedProgress.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false;
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

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 20
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getPage()
                isScrolling = false
            } else {
                binding.topRatedRecycler.setPadding(0,0,0,0)
            }
        }
    }

    private fun setupRecyclerView() {
        mAdapter = MoviesAdapter()
        binding.topRatedRecycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HomeFragment.scrollListener)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = view.findNavController()
        mAdapter.onItemClick = { topRatedMovies ->
            var opt = HomeFragmentDirections.actionNavigationHomeToNavigationMovie(topRatedMovies)
            navController.navigate(opt)
            Log.d("HomeFragment", "onViewCreated: ${topRatedMovies.title}")
        }
    }
}