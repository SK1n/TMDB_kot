package com.example.tmdb.ui.movies.nowPlaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NowPlayingViewModel : ViewModel() {
    val moviesPage: MutableLiveData<Resource<MoviesPage>> = MutableLiveData()
    var moviesPageNumber = 1
    var moviesPageResponse: MoviesPage? = null

    init {
        getMoviesPage()
    }

    fun getMoviesPage() = viewModelScope.launch {
        moviesPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getNowPlaying(page = moviesPageNumber)
        moviesPage.postValue(handleMoviesPageResponse(response))
    }

    private fun handleMoviesPageResponse(response: Response<MoviesPage>): Resource<MoviesPage> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                moviesPageNumber++
                if(moviesPageResponse == null) {
                    moviesPageResponse = resultResponse
                } else {
                    val oldMovies = moviesPageResponse?.results
                    val newMovies = resultResponse.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(moviesPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}