package com.example.tmdb.ui.detailsPerson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.models.*
import com.example.tmdb.repository.DetailsPersonRepository
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsPersonViewModel : ViewModel(){
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val personPage: MutableLiveData<Resource<PersonModel>> = MutableLiveData()

    val movies: MutableLiveData<Resource<PersonMovieModel>> = MutableLiveData()
    val tvShows: MutableLiveData<Resource<PersonTvShowModel>> = MutableLiveData()
    private val moviesResponse: PersonMovieModel? = null
    private val tvShowsResponse: PersonTvShowModel? = null
    private var personPageResponse: PersonModel? = null


    fun getMovies(id: Int) = viewModelScope.launch {
        val response = DetailsPersonRepository.getMovies(id)
        movies.postValue(handlePersonMovieResponse(response))
    }
    fun getTvShows(id: Int) = viewModelScope.launch {
        val response = DetailsPersonRepository.getTvShows(id)
        tvShows.postValue(handlePersonTvShowsResponse(response))
    }
    fun getPersonPage(id: Int) = viewModelScope.launch {
        val response = DetailsPersonRepository.getPersonPage(id)
        personPage.postValue(handlePersonPageResponse(response))
    }

    private fun handlePersonPageResponse(response: Response<PersonModel>): Resource<PersonModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(personPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handlePersonMovieResponse(response: Response<PersonMovieModel>): Resource<PersonMovieModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(moviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handlePersonTvShowsResponse(response: Response<PersonTvShowModel>): Resource<PersonTvShowModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(tvShowsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}