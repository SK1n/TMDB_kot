package com.example.tmdb.ui.tvShows.popularTv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.ui.tvShows.popularTv.data.PopularTvPagingSource
import com.example.tmdb.utils.Constants.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class PopularTvViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getData(): Flow<PagingData<TvShowModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { PopularTvPagingSource(RetrofitInstance.api) }
        ).flow
    }
}