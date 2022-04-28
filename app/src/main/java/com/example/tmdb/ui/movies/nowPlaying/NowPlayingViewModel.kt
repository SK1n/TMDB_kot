package com.example.tmdb.ui.movies.nowPlaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.MovieModelStatus
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.network.NowPlayingApi
import kotlinx.coroutines.launch


class NowPlayingViewModel : ViewModel() {
    private val _status = MutableLiveData<MovieModelStatus>()
    val status: LiveData<MovieModelStatus> = _status

    private val _moviesPage = MutableLiveData<MoviesPage>()
    private val moviesPage: LiveData<MoviesPage> = _moviesPage
    private val _movies = MutableLiveData<List<MoviesModel>>()
    val movies: LiveData<List<MoviesModel>> = _movies
    init {
       getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            _status.value = MovieModelStatus.LOADING
            try {
                _moviesPage.value = NowPlayingApi.retrofitService.getNowPlaying()
                _movies.value = moviesPage.value?.results
                _status.value = MovieModelStatus.DONE
            } catch (e: Exception) {
                _status.value = MovieModelStatus.ERROR
                _movies.value = listOf()
            }
        }
    }
}