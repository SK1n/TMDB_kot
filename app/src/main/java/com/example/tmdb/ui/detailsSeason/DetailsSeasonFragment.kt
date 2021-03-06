package com.example.tmdb.ui.detailsSeason

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
import com.example.tmdb.adapters.SeasonsDetailsAdapter
import com.example.tmdb.bindImage
import com.example.tmdb.databinding.FragmentDetailsSeasonBinding
import com.example.tmdb.utils.Resource
import com.example.tmdb.ui.customViews.MarginDecoration

class DetailsSeasonFragment : Fragment() {
    private var _binding: FragmentDetailsSeasonBinding? = null
    private val viewModel: DetailsSeasonViewModel by viewModels()
    private lateinit var eAdapter: SeasonsDetailsAdapter
    private val args: DetailsSeasonFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsSeasonBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupRecyclerView()
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = args.tvShow.name
        eAdapter.onItemClick = {
            Log.d("SeasonDetails", "onViewCreated: $it")
            val bundle = bundleOf("episode" to it, "tvShow" to args.tvShow)
            navController.navigate(R.id.navigation_episode, bundle)
        }
        getData()
        bind()
    }

    private fun bind() {
        val image =
            resources.getString(R.string.base_poster_path, args.season.poster_path)
        bindImage(binding.detailsBackdrop, image)
        bindImage(binding.detailsPoster, image)
        binding.summary.text =
            if (args.season.overview.isEmpty()) "No description found" else args.season.overview
        binding.detailsTitle.text = args.season.name
    }

    private fun getData() {
        viewModel.getCreditsPage(args.tvShow.id, args.season.season_number)
        viewModel.seasonDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        eAdapter.differ.submitList(it.episodes)
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
        binding.detailsProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.detailsProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        eAdapter = SeasonsDetailsAdapter()
        binding.seasonDetailsRecyclerView.apply {
            adapter = eAdapter
            addItemDecoration(MarginDecoration(context))
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
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