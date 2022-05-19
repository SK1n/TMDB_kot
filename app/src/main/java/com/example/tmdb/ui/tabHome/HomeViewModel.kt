package com.example.tmdb.ui.tabHome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.repository.MoviesTabHomeRepository
import kotlinx.coroutines.flow.Flow


class HomeViewModel() : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getData(): Flow<PagingData<MoviesModel>> {
        return MoviesTabHomeRepository.getData()
    }
}
