package com.example.tmdb.ui.tabHome.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.tmdb.api.ApiService
import com.example.tmdb.models.MoviesModel
import com.example.tmdb.utils.Constants.QUERY_PAGE_SIZE
import java.io.IOException

class HomePagingSource(
    private val service: ApiService
) : PagingSource<Int, MoviesModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel> {
        val position = params.key ?: 1
        return try {
            val response = service.getTopRatedMovies(page = position)
            val movies = response.body()?.results
            val nextKey = if (movies!!.isEmpty()) {
                null
            } else {
                position + (params.loadSize / QUERY_PAGE_SIZE)
            }
            LoadResult.Page(
                data = movies,
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