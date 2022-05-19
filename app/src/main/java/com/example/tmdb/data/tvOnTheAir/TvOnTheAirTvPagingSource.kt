package com.example.tmdb.data.tvOnTheAir

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.tmdb.api.ApiService
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.utils.Constants.FIRST_PAGE
import com.example.tmdb.utils.Constants.QUERY_PAGE_SIZE
import java.io.IOException

class TvOnTheAirTvPagingSource(private val service: ApiService) : PagingSource<Int, TvShowModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowModel> {
        val position = params.key ?: FIRST_PAGE
        return try {
            val response = service.getTvOnTheAir(page = position)
            val tvShows = response.body()?.results
            val nextKey = if (tvShows!!.isEmpty()) {
                null
            } else {
                position + (params.loadSize / QUERY_PAGE_SIZE)
            }
            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == FIRST_PAGE) null else position,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, TvShowModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}