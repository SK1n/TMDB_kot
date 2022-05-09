package com.example.tmdb.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.tmdb.api.ApiService
import com.example.tmdb.models.TvShowModel
import java.io.IOException

class TmdbApiSource(private val service: ApiService): PagingSource<Int, TvShowModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowModel> {
        val position = params.key ?: 1
        return try {
            val response = service.getTvTopRated(page = position)
            val repos = response.body()?.results
            val nextKey = if(repos!!.isEmpty()) {
                null
            } else {
                position + (params.loadSize / 20)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == 1) null else position,
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