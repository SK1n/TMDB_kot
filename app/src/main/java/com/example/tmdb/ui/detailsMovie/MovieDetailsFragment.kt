package com.example.tmdb.ui.detailsMovie

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tmdb.R
import com.example.tmdb.adapters.CastAdapter
import com.example.tmdb.bindImage
import com.example.tmdb.databinding.FragmentMovieDetailsBinding
import com.example.tmdb.utils.Resource

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var pagerAdapter: CastAdapter
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        navController = findNavController()
        viewModel.creditsPage.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { castResponse ->
                        pagerAdapter.differ.submitList(castResponse.cast)
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
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun hideProgressBar() {
        binding.detailsProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.detailsProgressBar.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter.onItemClick = {
            val bundle = bundleOf("person" to it)
            navController.navigate(R.id.navigation_person, bundle)
        }
        viewModel.getCreditsPage(args.movie.id!!)
        (activity as AppCompatActivity).supportActionBar?.title = args.movie.title
        bind()
    }


    private fun bind() {
        val detailsBackdrop = resources.getString(R.string.base_poster_path, args.movie.poster_path)
        bindImage(binding.detailsBackdrop, detailsBackdrop)
        bindImage(binding.detailsPoster, detailsBackdrop)
        binding.detailsTitle.text = args.movie.title
        binding.summary.text = args.movie.overview
        binding.detailsReleaseDate.text =
            resources.getString(R.string.release, args.movie.release_date)
        binding.detailsRating.text =
            resources.getString(R.string.rating, args.movie.vote_average.toString())
    }

    private fun setupRecyclerView() {
        pagerAdapter = CastAdapter()
        binding.detailsRecyclerView.apply {
            adapter = pagerAdapter
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
                navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
