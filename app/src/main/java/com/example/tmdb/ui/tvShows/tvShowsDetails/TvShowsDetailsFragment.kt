package com.example.tmdb.ui.tvShows.tvShowsDetails

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.adapters.SeasonsAdapter
import com.example.tmdb.bindImage
import com.example.tmdb.databinding.FragmentTvShowsDetailsBinding
import com.example.tmdb.utils.Resource
import com.example.tmdb.widgets.MarginDecoration

class TvShowsDetailsFragment : Fragment() {
    private var _binding: FragmentTvShowsDetailsBinding? = null
    private val viewModel: TvShowsDetailsViewModel by viewModels()
    val args: TvShowsDetailsFragmentArgs by navArgs()
    private lateinit var sAdapter: SeasonsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowsDetailsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as  AppCompatActivity).supportActionBar?.title = args.tvShow.name
        getData()
        bind()
    }

    private fun getData() {
        viewModel.getCreditsPage(args.tvShow.id)
        viewModel.tvShowDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        sAdapter.differ.submitList(it.seasons)
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
    }

    private fun bind() {
        val detailsBackdrop =
            resources.getString(R.string.base_poster_path, args.tvShow.poster_path)
        bindImage(binding.detailsBackdrop, detailsBackdrop)
        bindImage(binding.detailsPoster, detailsBackdrop)
        binding.detailsTitle.text = args.tvShow.name
        binding.summary.text = args.tvShow.overview
        binding.detailsReleaseDate.text =
            resources.getString(R.string.release, args.tvShow.first_air_date)
        binding.detailsRating.text =
            resources.getString(R.string.rating, args.tvShow.vote_average.toString())
    }

    private fun hideProgressBar() {
        binding.detailsProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.detailsProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        sAdapter = SeasonsAdapter()
        binding.seasonRecyclerView.apply {
            adapter = sAdapter
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