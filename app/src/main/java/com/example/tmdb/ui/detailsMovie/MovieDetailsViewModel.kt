package com.example.tmdb.ui.detailsMovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.models.CreditsModel
import com.example.tmdb.repository.DetailsMovieRepository
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieDetailsViewModel : ViewModel() {
    val creditsPage: MutableLiveData<Resource<CreditsModel>> = MutableLiveData()
    var creditsPageResponse: CreditsModel? = null

    fun getCreditsPage(id: Int) = viewModelScope.launch {
        val response = DetailsMovieRepository.getCreditsPage(
            id = id
        )
        creditsPage.postValue(handleCreditsPageResponse(response))
    }

    private fun handleCreditsPageResponse(response: Response<CreditsModel>): Resource<CreditsModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(creditsPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}