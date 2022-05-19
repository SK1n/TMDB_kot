package com.example.tmdb.ui.tvPopular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.repository.TvShowsPopularRepository
import kotlinx.coroutines.flow.Flow

class PopularTvViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getData(): Flow<PagingData<TvShowModel>> {
        return TvShowsPopularRepository.getData()
    }
}