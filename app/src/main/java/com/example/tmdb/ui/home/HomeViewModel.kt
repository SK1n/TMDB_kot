package com.example.tmdb.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.ui.home.data.HomePagingSource
import com.example.tmdb.utils.Constants
import kotlinx.coroutines.flow.Flow


class HomeViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getData(): Flow<PagingData<MoviesModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomePagingSource(RetrofitInstance.api) }
        ).flow
    }
}
