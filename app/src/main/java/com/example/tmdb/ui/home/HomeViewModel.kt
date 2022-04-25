package com.example.tmdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.api.GetMoviesResponse
import com.example.tmdb.ui.home.retrofit.Movie
import com.example.tmdb.ui.home.retrofit.MoviesRepository
import com.example.tmdb.ui.home.retrofit.PopularMovies

class HomeViewModel(val repo: MoviesRepository, val req: GetMoviesResponse, val movies: PopularMovies): ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()


    suspend fun fetchMovies(page: Int) {
        movieList.value = repo.getPopularMovies(movies,req)
    }

    fun getMovieList(): LiveData<List<Movie>> = movieList

    fun setMovieList(movieList: List<Movie>){
        this.movieList.value = movieList
    }
}
