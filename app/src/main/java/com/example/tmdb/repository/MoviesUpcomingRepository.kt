package com.example.tmdb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.data.moviesUpcoming.UpcomingPagingSource
import com.example.tmdb.utils.Constants
import kotlinx.coroutines.flow.Flow

object MoviesUpcomingRepository {
    fun getData(): Flow<PagingData<MoviesModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.QUERY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UpcomingPagingSource(RetrofitInstance.api) }
        ).flow
    }
}