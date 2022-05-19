package com.example.tmdb.ui.moviesUpcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.repository.MoviesUpcomingRepository
import kotlinx.coroutines.flow.Flow

class UpcomingViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return MoviesUpcomingRepository.getData()
    }
}