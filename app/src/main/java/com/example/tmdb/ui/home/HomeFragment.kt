package com.example.tmdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.databinding.MovieItemBinding
import com.example.tmdb.ui.home.adapter.MoviesAdapter
import com.example.tmdb.ui.home.retrofit.Movie
import com.example.tmdb.ui.home.retrofit.MoviesRepository

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.java.simpleName
    private var _binding: FragmentHomeBinding? = null
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private val model: HomeViewModel? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        popularMovies = binding.popularMovies
        popularMovies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        popularMoviesAdapter = MoviesAdapter(listOf())
        popularMovies.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        popularMovies.adapter = popularMoviesAdapter




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navController = view?.findNavController()
        popularMoviesAdapter.onItemClick = { movie ->
            var opt = HomeFragmentDirections.actionNavigationHomeToNavigationMovie()
            navController?.navigate(opt)
        }
    }


    private fun onPopularMoviesFetched(movies: List<Movie>) {

        model?.getMovieList()?.observe(viewLifecycleOwner) {

            Log.d(TAG, "onPopularMoviesFetched: $it")

            it?.let { popularMoviesAdapter.updateMovies(movies) }
        }
        Log.d("Data", movies.toString())
    }

    private fun onError() {
        Toast.makeText(context, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
