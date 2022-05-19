package com.example.tmdb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.data.moviesPopular.PopularPagingSource
import com.example.tmdb.utils.Constants
import kotlinx.coroutines.flow.Flow

object MoviesPopularRepository {
    fun getData(): Flow<PagingData<MoviesModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.QUERY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PopularPagingSource(RetrofitInstance.api) }
        ).flow
    }
}