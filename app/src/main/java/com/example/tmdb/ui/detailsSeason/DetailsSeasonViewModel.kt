package com.example.tmdb.ui.detailsSeason

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.models.SeasonDetailModel
import com.example.tmdb.repository.DetailsSeasonRepository
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsSeasonViewModel : ViewModel() {
    val seasonDetails: MutableLiveData<Resource<SeasonDetailModel>> = MutableLiveData()
    var seasonDetailResponse: SeasonDetailModel? = null

    fun getCreditsPage(id: Int, season: Int) = viewModelScope.launch {
        seasonDetails.postValue(Resource.Loading())
        val response = DetailsSeasonRepository.getCreditsPage(
            id = id,
            season = season,
        )
        seasonDetails.postValue(handleSeasonDetailsResponse(response))
    }

    private fun handleSeasonDetailsResponse(response: Response<SeasonDetailModel>): Resource<SeasonDetailModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(seasonDetailResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}