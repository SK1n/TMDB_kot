package com.example.tmdb.ui.tvShows.topRatedTv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowsPageModel
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TopRatedTvViewModel : ViewModel(){
    val topRatedTvPage: MutableLiveData<Resource<TvShowsPageModel>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var topRatedTvPageNumber = 1
    var topRatedPageResponse: TvShowsPageModel? = null

    init {
        getPage()
    }

    fun getPage() = viewModelScope.launch {
        topRatedTvPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getTvTopRated(page = topRatedTvPageNumber)
        topRatedTvPage.postValue(handleTvShowsPageResponse(response))
    }

    private fun handleTvShowsPageResponse(response: Response<TvShowsPageModel>): Resource<TvShowsPageModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                topRatedTvPageNumber++
                if(topRatedPageResponse == null) {
                    topRatedPageResponse = resultResponse
                } else {
                    val oldTvShows = topRatedPageResponse?.results
                    val newTvShows = resultResponse.results
                    oldTvShows?.addAll(newTvShows)
                }
                return Resource.Success(topRatedPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}