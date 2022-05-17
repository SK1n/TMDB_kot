package com.example.tmdb.ui.episode

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.tmdb.R
import com.example.tmdb.adapters.CastAdapter
import com.example.tmdb.adapters.GuestAdapter
import com.example.tmdb.adapters.SeasonsDetailsAdapter
import com.example.tmdb.bindImage
import com.example.tmdb.databinding.FragmentEpisodeBinding
import com.example.tmdb.goneIfNull
import com.example.tmdb.utils.Resource
import com.example.tmdb.widgets.MarginDecoration

class EpisodeFragment : Fragment() {
    private val viewModel: EpisodeViewModel by viewModels()
    private var _binding: FragmentEpisodeBinding? = null
    private lateinit var gAdapter: GuestAdapter
    private val binding get() = _binding!!
    val args: EpisodeFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        bind()
    }

    private fun bind() {
        val image =
            resources.getString(R.string.base_poster_path, args.episode.still_path)
        val imagePoster =
            resources.getString(R.string.base_poster_path, args.tvShow.poster_path)
        bindImage(binding.detailsBackdrop, image)
        bindImage(binding.detailsPoster, imagePoster)
        binding.detailsRating.text = resources.getString(R.string.rating, args.episode.vote_average)
        binding.detailsReleaseDate.text = resources.getString(R.string.release,args.episode.air_date)
        binding.detailsTitle.text = args.episode.name
        binding.summary.text = args.episode.overview
        goneIfNull(binding.detailsReleaseDate,args.episode.air_date)
        goneIfNull(binding.summaryLabel,args.episode.overview)
        goneIfNull(binding.summary,args.episode.overview)
    }

    private fun getData() {
        viewModel.getCreditsPage(id = args.tvShow.id, season = args.episode.season_number, episode = args.episode.episode_number)
        viewModel.episodeDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        gAdapter.differ.submitList(it.guest_stars)
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
        gAdapter = GuestAdapter()
        binding.detailsRecyclerView.apply {
            adapter = gAdapter
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