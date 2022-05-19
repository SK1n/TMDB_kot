package com.example.tmdb.ui.moviesNowPlaying

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.repository.MoviesNowPlayingRepository
import kotlinx.coroutines.flow.Flow


class NowPlayingViewModel() : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return MoviesNowPlayingRepository.getData()
    }
}