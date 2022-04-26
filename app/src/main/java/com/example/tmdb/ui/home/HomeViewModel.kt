package com.example.tmdb.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.network.TopRatedApi
import com.example.tmdb.network.TopRatedMovies
import kotlinx.coroutines.launch

enum class TopRatedApiStatus { LOADING, ERROR, DONE }
class HomeViewModel(): ViewModel() {

    private val _status = MutableLiveData<TopRatedApiStatus>()
    val status: LiveData<TopRatedApiStatus> = _status

    private val _movies = MutableLiveData<List<TopRatedMovies>>()
    val movies: LiveData<List<TopRatedMovies>> = _movies
    init {
        getTopRatedMovieImage()
    }

    private fun getTopRatedMovieImage() {
        viewModelScope.launch {
            _status.value = TopRatedApiStatus.LOADING
            try {
                _movies.value = TopRatedApi.retrofitService.getTopRatedMovies()
                _status.value = TopRatedApiStatus.DONE
            } catch (e: Exception) {
                _status.value = TopRatedApiStatus.ERROR
                _movies.value = listOf()
            }
        }
    }
}
