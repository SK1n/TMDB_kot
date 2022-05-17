package com.example.tmdb.ui.person

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tmdb.MainActivity
import com.example.tmdb.R
import com.example.tmdb.adapters.PersonMovieAdapter
import com.example.tmdb.adapters.PersonTvShowAdapter
import com.example.tmdb.bindImage
import com.example.tmdb.databinding.FragmentPersonBinding
import com.example.tmdb.models.PersonModel
import com.example.tmdb.utils.Resource
import com.example.tmdb.widgets.MarginDecoration

class PersonFragment : Fragment() {
    private val viewModel: PersonViewModel by viewModels()
    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: PersonMovieAdapter
    private lateinit var tAdapter: PersonTvShowAdapter
    val args: PersonFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPersonPage(args.person.id)
        viewModel.getMovies(args.person.id)
        viewModel.getTvShows(args.person.id)
        var navController = findNavController()
        mAdapter.onItemClick = {
            val bundle = bundleOf("movie" to it)
            navController.navigate(R.id.navigation_movie, bundle)
        }
        tAdapter.onItemClick = {
            val bundle = bundleOf("tvShow" to it)
            navController.navigate(R.id.navigation_tv_shows_details, bundle)
        }
        getPersonPage()
        getPersonMovie()
        getPersonTvShows()
    }

    private fun getPersonMovie() {
        viewModel.movies.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { lResponse ->
                        mAdapter.differ.submitList(lResponse.cast)
                        Log.d("Person", "getPersonMovie: $lResponse")
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

    private fun getPersonPage() {
        viewModel.personPage.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { castResponse ->
                        bind(castResponse)
                        Log.d("Person", "onViewCreated: $castResponse")
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

    private fun hideProgressBar() {
        viewModel.isLoading.value = false
    }

    private fun showProgressBar() {
        viewModel.isLoading.value = true
    }

    fun bind(response: PersonModel) {
        val profilePath = resources.getString(R.string.base_poster_path, response.profile_path)
        (requireActivity() as MainActivity).title = response.name
        binding.personName.text = response.name
        binding.personBirthday.text = "Birth day: " + response.birthday
        binding.personDeathDay.text =
            if (response.deathday != null) "Death day: " + response.deathday.toString() else "   "
        binding.personBiography.text = response.biography
        bindImage(binding.personProfilePath, profilePath)
    }

    private fun getPersonTvShows() {
        viewModel.tvShows.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { response ->
                        tAdapter.differ.submitList(response.cast)
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

    private fun setupRecyclerView() {
        mAdapter = PersonMovieAdapter()
        binding.movieRecycler.apply {
            adapter = mAdapter
            addItemDecoration(MarginDecoration(context))
        }
        tAdapter = PersonTvShowAdapter()
        binding.tvShowsRecycler.apply {
            adapter = tAdapter
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