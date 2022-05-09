package com.example.tmdb.ui.tvShows.popularTv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowsPageModel
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PopularTvViewModel: ViewModel() {
    val popularTvShowPage: MutableLiveData<Resource<TvShowsPageModel>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var popularTvShowPageNumber = 1
    var popularTvShowPageResponse: TvShowsPageModel? = null

    init {
        getPage()
    }

    fun getPage() = viewModelScope.launch {
        popularTvShowPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getTvPopular(page = popularTvShowPageNumber)
        popularTvShowPage.postValue(handleTvShowsPageResponse(response))
    }

    private fun handleTvShowsPageResponse(response: Response<TvShowsPageModel>): Resource<TvShowsPageModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                popularTvShowPageNumber++
                if(popularTvShowPageResponse == null) {
                    popularTvShowPageResponse = resultResponse
                } else {
                    val oldTvShow = popularTvShowPageResponse?.results
                    val newTvShow = resultResponse.results
                    oldTvShow?.addAll(newTvShow)
                }
                return Resource.Success(popularTvShowPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}