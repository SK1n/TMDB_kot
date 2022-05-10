package com.example.tmdb.ui.tvShows.topRatedTv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.ui.tvShows.topRatedTv.data.TopRatedTvPagingSource
import com.example.tmdb.utils.Constants
import kotlinx.coroutines.flow.Flow

class TopRatedTvViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<TvShowModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopRatedTvPagingSource(RetrofitInstance.api) }
        ).flow
    }
}