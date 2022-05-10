package com.example.tmdb.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class HomeViewModel : ViewModel() {


    val moviesPage: MutableLiveData<Resource<MoviesPage>> = MutableLiveData()
    var moviesPageNumber = 1
    var moviesPageResponse: MoviesPage? = null

    init {
        getPage()
    }

    fun getPage() = viewModelScope.launch {
        moviesPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getTopRatedMovies(page = moviesPageNumber)
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
