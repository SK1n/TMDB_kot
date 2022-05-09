package com.example.tmdb.ui.tvShows.tvOnTheAir

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowsPageModel
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TvOnTheAirViewModel : ViewModel(){
    val tvShowPage: MutableLiveData<Resource<TvShowsPageModel>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var tvShowPageNumber = 1
    var tvShowPageResponse: TvShowsPageModel? = null

    init {
        getPage()
    }

    fun getPage() = viewModelScope.launch {
        tvShowPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getTvOnTheAir(page = tvShowPageNumber)
        tvShowPage.postValue(handleTvShowsPageResponse(response))
    }

    private fun handleTvShowsPageResponse(response: Response<TvShowsPageModel>): Resource<TvShowsPageModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                tvShowPageNumber++
                if(tvShowPageResponse == null) {
                    tvShowPageResponse = resultResponse
                } else {
                    val oldTvShows = tvShowPageResponse?.results
                    val newTvShows = resultResponse.results
                    oldTvShows?.addAll(newTvShows)
                }
                return Resource.Success(tvShowPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}