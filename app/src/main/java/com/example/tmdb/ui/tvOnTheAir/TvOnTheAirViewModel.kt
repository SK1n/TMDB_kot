package com.example.tmdb.ui.tvOnTheAir

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.repository.TvOnTheAirRepository
import kotlinx.coroutines.flow.Flow

class TvOnTheAirViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getData(): Flow<PagingData<TvShowModel>> {
        return TvOnTheAirRepository.getData()
    }
}