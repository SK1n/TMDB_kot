package com.example.tmdb.ui.movies.nowPlaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.databinding.FragmentNowPlayingMoviesBinding
import com.example.tmdb.ui.home.HomeViewModel


class NowPlayingFragment : Fragment() {
    private val viewModel: NowPlayingViewModel by viewModels()
    private var _binding: FragmentNowPlayingMoviesBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesRecycler: RecyclerView
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNowPlayingMoviesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        moviesRecycler = binding.topRatedRecycler
        moviesRecycler.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        moviesAdapter = MoviesAdapter()
        moviesRecycler.adapter = moviesAdapter
        setHasOptionsMenu(true)
        return binding.root
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
