package com.example.tmdb.ui.tvTopRated

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.repository.TvTopRatedRepository
import kotlinx.coroutines.flow.Flow

class TopRatedTvViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<TvShowModel>> {
        return TvTopRatedRepository.getData()
    }
}