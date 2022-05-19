package com.example.tmdb.ui.detailsEpisode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.models.EpisodeModel
import com.example.tmdb.repository.DetailsEpisodeRepository
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsEpisodeViewModel : ViewModel() {
    val episodeDetails: MutableLiveData<Resource<EpisodeModel>> = MutableLiveData()
    var episodeDetailResponse: EpisodeModel? = null

    fun getCreditsPage(id: Int, season: Int, episode: Int) = viewModelScope.launch {
        episodeDetails.postValue(Resource.Loading())
        val response = DetailsEpisodeRepository.getCreditsPage(
            id = id,
            season = season,
            episode = episode
        )
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