package com.example.tmdb.ui.tvShows.popularTv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.ApiService
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.models.TvShowsPageModel
import com.example.tmdb.utils.Resource
import com.example.tmdb.utils.TmdbApiSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class PopularTvViewModel: ViewModel() {
    val popularTvShowPage: MutableLiveData<Resource<TvShowsPageModel>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var popularTvShowPageNumber = 1
    var popularTvShowPageResponse: TvShowsPageModel? = null

    init {
       // getPage()
    }

    fun getPage() = viewModelScope.launch {
        popularTvShowPage.postValue(Resource.Loading())
        val response = RetrofitInstance.api.getTvPopular(page = popularTvShowPageNumber)
        popularTvShowPage.postValue(handleTvShowsPageResponse(response))
    }

    private fun handleTvShowsPageResponse(response: Response<TvShowsPageModel>): Resource<TvShowsPageModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                popularTvShowPageNumber++
                if(popularTvShowPageResponse == null) {
                    popularTvShowPageResponse = resultResponse
                } else {
                    val oldTvShow = popularTvShowPageResponse?.results
                    val newTvShow = resultResponse.results
                    oldTvShow?.addAll(newTvShow)
                }
                return Resource.Success(popularTvShowPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

        fun getData(): Flow<PagingData<TvShowModel>> {
            return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { TmdbApiSource(RetrofitInstance.api) }
            ).flow
        }

        companion object {
            const val NETWORK_PAGE_SIZE = 20
        }


}