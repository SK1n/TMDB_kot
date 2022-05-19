package com.example.tmdb.ui.moviesPopular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.repository.MoviesPopularRepository
import kotlinx.coroutines.flow.Flow

class PopularMoviesViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return MoviesPopularRepository.getData()
    }
}