package com.example.tmdb.ui.tvShows.tvOnTheAir

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TvOnTheAirViewModel : ViewModel(){
    val tvShowPage: MutableLiveData<Resource<MoviesPage>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var tvShowPageNumber = 1
    var tvShowPageResponse: MoviesPage? = null

    init {
        getMoviesPage()
    }

    fun getMoviesPage() = viewModelScope.launch {
        tvShowPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getNowPlaying(page = tvShowPageNumber)
        tvShowPage.postValue(handleMoviesPageResponse(response))
    }

    private fun handleMoviesPageResponse(response: Response<MoviesPage>): Resource<MoviesPage> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                tvShowPageNumber++
                if(tvShowPageResponse == null) {
                    tvShowPageResponse = resultResponse
                } else {
                    val oldMovies = tvShowPageResponse?.results
                    val newMovies = resultResponse.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(tvShowPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}