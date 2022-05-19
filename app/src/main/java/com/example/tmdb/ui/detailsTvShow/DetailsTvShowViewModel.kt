package com.example.tmdb.ui.detailsTvShow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.models.TvShowsDetails
import com.example.tmdb.repository.DetailsTvShowRepository
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsTvShowViewModel : ViewModel() {
    val tvShowDetails: MutableLiveData<Resource<TvShowsDetails>> = MutableLiveData()
    var tvShowResponse: TvShowsDetails? = null

    fun getCreditsPage(id: Int) = viewModelScope.launch {
        tvShowDetails.postValue(Resource.Loading())
        val response = DetailsTvShowRepository.getCreditsPage(
            id = id,
        )
        tvShowDetails.postValue(handleSeasonResponse(response))
    }

    private fun handleSeasonResponse(response: Response<TvShowsDetails>): Resource<TvShowsDetails> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(tvShowResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}