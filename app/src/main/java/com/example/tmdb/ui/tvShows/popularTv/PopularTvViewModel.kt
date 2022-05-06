package com.example.tmdb.ui.tvShows.popularTv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PopularTvViewModel: ViewModel() {
    val popularTvShowPage: MutableLiveData<Resource<MoviesPage>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var popularTvShowPageNumber = 1
    var popularTvShowPageResponse: MoviesPage? = null

    init {
        getMoviesPage()
    }

    fun getMoviesPage() = viewModelScope.launch {
        popularTvShowPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getNowPlaying(page = popularTvShowPageNumber)
        popularTvShowPage.postValue(handleMoviesPageResponse(response))
    }

    private fun handleMoviesPageResponse(response: Response<MoviesPage>): Resource<MoviesPage> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                popularTvShowPageNumber++
                if(popularTvShowPageResponse == null) {
                    popularTvShowPageResponse = resultResponse
                } else {
                    val oldMovies = popularTvShowPageResponse?.results
                    val newMovies = resultResponse.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(popularTvShowPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}