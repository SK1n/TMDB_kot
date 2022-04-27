package com.example.tmdb.ui.home.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdb.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
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
