package com.example.tmdb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.data.tvTopRated.TopRatedTvPagingSource
import com.example.tmdb.utils.Constants
import kotlinx.coroutines.flow.Flow

object TvTopRatedRepository {
    fun getData(): Flow<PagingData<TvShowModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.QUERY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopRatedTvPagingSource(RetrofitInstance.api) }
        ).flow
    }
}