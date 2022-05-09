package com.example.tmdb.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.ApiService
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.utils.TmdbApiSource
import kotlinx.coroutines.flow.Flow

class TmdbRepo(private val service: ApiService) {
    fun getSearchResultStream(query: String): Flow<PagingData<TvShowModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {TmdbApiSource(service)}
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}