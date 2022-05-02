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
import androidx.viewpager2.widget.ViewPager2
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.databinding.FragmentUpcomingMoviesBinding
import com.example.tmdb.ui.movies.popular.PopularViewModel
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
        viewModel.moviesPage.observe(viewLifecycleOwner, Observer {
                response -> when(response) {
            is  Resource.Success -> {
                hideProgressBar()
                response.data?.let {
                        moviesResponse ->
                    moviesAdapter.differ.submitList(moviesResponse.results)
                    val totalPages = moviesResponse.total_pages / Constants.QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.moviesPageNumber == totalPages
                }
            }
            is Resource.Error -> {
                hideProgressBar()
                response.message?.let { message ->
                    Log.d(ContentValues.TAG, "An error occured: $message")
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
        binding.upcomingProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }
    private fun showProgressBar() {
        binding.upcomingProgressBar.visibility = View.VISIBLE
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
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getMoviesPage()
                isScrolling = false
            } else {
                binding.upcomingRecycler.setPadding(0,0,0,0)
            }
        }
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.upcomingRecycler.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@UpcomingFragment.scrollListener)
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
