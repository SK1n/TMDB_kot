package com.example.tmdb.data.moviesPopular

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.tmdb.api.ApiService
import com.example.tmdb.models.MoviesModel
import java.io.IOException

class PopularPagingSource(
    private val service: ApiService
) : PagingSource<Int, MoviesModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel> {
        val position = params.key ?: 1
        return try {
            val response = service.getPopular(page = position)
            val tvShows = response.body()?.results
            val nextKey = if (tvShows!!.isEmpty()) {
                null
            } else {
                position + (params.loadSize / 20)
            }
            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == 1) null else position,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MoviesModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}