package com.example.tmdb.ui.movies.nowPlaying

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.ui.movies.nowPlaying.data.NowPlayingPagingSource
import com.example.tmdb.ui.tvShows.tvOnTheAir.data.TvOnTheAirTvPagingSource
import com.example.tmdb.utils.Constants
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response


class NowPlayingViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.QUERY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NowPlayingPagingSource(RetrofitInstance.api) }
        ).flow
    }
}