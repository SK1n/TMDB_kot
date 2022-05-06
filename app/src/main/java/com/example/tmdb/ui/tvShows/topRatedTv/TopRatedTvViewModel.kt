package com.example.tmdb.ui.tvShows.topRatedTv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TopRatedTvViewModel : ViewModel(){
    val topRatedTvPage: MutableLiveData<Resource<MoviesPage>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var topRatedTvPageNumber = 1
    var topRatedPageResponse: MoviesPage? = null

    init {
        getMoviesPage()
    }

    fun getMoviesPage() = viewModelScope.launch {
        topRatedTvPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getNowPlaying(page = topRatedTvPageNumber)
        topRatedTvPage.postValue(handleMoviesPageResponse(response))
    }

    private fun handleMoviesPageResponse(response: Response<MoviesPage>): Resource<MoviesPage> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                topRatedTvPageNumber++
                if(topRatedPageResponse == null) {
                    topRatedPageResponse = resultResponse
                } else {
                    val oldMovies = topRatedPageResponse?.results
                    val newMovies = resultResponse.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(topRatedPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}