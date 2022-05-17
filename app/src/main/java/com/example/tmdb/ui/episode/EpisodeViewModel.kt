package com.example.tmdb.ui.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.EpisodeModel
import com.example.tmdb.models.SeasonDetailModel
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class EpisodeViewModel: ViewModel() {
    val episodeDetails: MutableLiveData<Resource<EpisodeModel>> = MutableLiveData()
    var episodeDetailResponse: EpisodeModel? = null

    fun getCreditsPage(id: Int, season: Int,episode: Int) = viewModelScope.launch {
        episodeDetails.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getEpisode(id = id, seasonNumber = season, episodeNumber = episode)
        episodeDetails.postValue(handleEpisodeDetailsResponse(response))
    }

    private fun handleEpisodeDetailsResponse(response: Response<EpisodeModel>): Resource<EpisodeModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(episodeDetailResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}