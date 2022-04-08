package com.example.tmdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        popularMovies = binding.popularMovies
        popularMovies.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        popularMoviesAdapter = MoviesAdapter(listOf())
        popularMovies.adapter = popularMoviesAdapter
        MoviesRepository.getPopularMovies(onSuccess = ::onPopularMoviesFetched,onError =  ::onError)

        return root
    }
    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.updateMovies(movies)
    }

    private fun onError() {
       // Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
