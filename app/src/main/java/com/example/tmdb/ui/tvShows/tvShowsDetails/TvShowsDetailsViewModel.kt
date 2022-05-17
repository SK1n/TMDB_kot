package com.example.tmdb.ui.tvShows.tvShowsDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowSeasonsModel
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TvShowsDetailsViewModel : ViewModel() {
    val tvShowDetails: MutableLiveData<Resource<TvShowSeasonsModel>> = MutableLiveData()
    var tvShowResponse: TvShowSeasonsModel? = null

    fun getCreditsPage(id: Int) = viewModelScope.launch {
        tvShowDetails.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getSeasons(id)
        tvShowDetails.postValue(handleSeasonResponse(response))
    }

    private fun handleSeasonResponse(response: Response<TvShowSeasonsModel>): Resource<TvShowSeasonsModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(tvShowResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}