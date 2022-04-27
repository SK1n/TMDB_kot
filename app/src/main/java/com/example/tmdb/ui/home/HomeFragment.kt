package com.example.tmdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var topRatedRecycler: RecyclerView

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        topRatedRecycler = binding.topRatedRecycler
        topRatedRecycler.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        topRatedAdapter = TopRatedAdapter()
        topRatedRecycler.adapter = topRatedAdapter
        setHasOptionsMenu(true)
        return binding.root
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
        var navController = view?.findNavController()

        topRatedAdapter.onItemClick = { topRatedMovies ->
            var opt = HomeFragmentDirections.actionNavigationHomeToNavigationMovie()
            navController?.navigate(opt)
            Log.d("HomeFragment", "onViewCreated: ${topRatedMovies.title}")
        }
    }
}